package backend.API.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import backend.API.security.filters.JwtRequestFilter;
import backend.API.security.services.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter{
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	//@Override
	/*protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(myUserDetailsService);
	}*/
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//auth.userDetailsService(myUserDetailsService);
		auth.authenticationProvider(daoAuthenticationProvider());
		//auth.userDetailsService(myUserDetailsService);
    }
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/evcharge/api/login").permitAll()
			.antMatchers("/evcharge/api/admin/healthcheck").permitAll()
			.antMatchers("/evcharge/api/admin/resetsessions").permitAll()
			.antMatchers("/web/**").permitAll()
			//.anyRequest().permitAll()
			.antMatchers("/evcharge/api/admin/usermod/*").hasAuthority("isAdmin")
			.antMatchers("/evcharge/api/admin/users/*").hasAuthority("isAdmin")
			.antMatchers("/evcharge/api/admin/system/*").hasAuthority("isAdmin")
			.anyRequest().authenticated()
			//.anyRequest().permitAll()
			.and().sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	  public AuthenticationProvider daoAuthenticationProvider() {
	    DaoAuthenticationProvider provider = 
	      new DaoAuthenticationProvider();
	    provider.setPasswordEncoder(passwordEncoder());
	    provider.setUserDetailsService(myUserDetailsService);
	    return provider;
	  } 
	
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
	   return super.authenticationManagerBean();
	} 
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		//return NoOpPasswordEncoder.getInstance();
	}
	

}
