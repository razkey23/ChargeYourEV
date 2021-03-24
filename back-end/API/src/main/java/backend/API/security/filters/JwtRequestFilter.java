package backend.API.security.filters;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import backend.API.security.services.MyUserDetailsService;
import backend.API.security.util.JwtUtil;
import backend.API.service.JwtBlacklistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	@Autowired
	private MyUserDetailsService userDetailsService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private JwtBlacklistService jwtbls;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authorizationHeader=request.getHeader("X-OBSERVATORY-AUTH");
		String username=null;
		String jwt=null;
		if (authorizationHeader !=null && authorizationHeader.startsWith("Bearer ")) {
			jwt=authorizationHeader.substring(7);
			username=jwtUtil.extractUsername(jwt);
			if(jwtbls.isInJwtBlacklist(authorizationHeader)) {
				username=null; //BLACKLIST CHECK
				jwt=null;
			}
		}
		if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
			if (jwtUtil.validateToken(jwt, userDetails)) {
				System.out.println(userDetails.getAuthorities());
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			
		}
		filterChain.doFilter(request, response);	
	}
	

}

