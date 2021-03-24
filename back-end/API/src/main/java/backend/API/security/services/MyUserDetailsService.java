package backend.API.security.services;

import backend.API.model.Users;
import backend.API.service.UsersService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService{
	@Autowired
	UsersService usersService;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
		Users user = usersService.getUserByUsername(userName);
		System.out.println(user.getUsername());
		String x= "notAdmin";
		if (user.isAdmin()) {
			x="isAdmin";
		}
		SimpleGrantedAuthority authority =new SimpleGrantedAuthority(x);
		
		//System.out.println((UserDetails) user);
		//if (user !=null) return (UserDetails) user;
		//else return null;
	  List<SimpleGrantedAuthority> authorities=new ArrayList<SimpleGrantedAuthority>();
	  authorities.add(authority);
	  return new User(user.getUsername(),user.getPassword(),authorities);
	  //return new User("foo","$2a$10$6D7bJIspdvqwTQSodFOQEu1T/Oa/JB..acWDjjheC8NZYIKlZXel.",authorities);
	  //return new User("foo","$2a$10$Cmu5BisoteEehMNz6CyMouNjyKeMK2kMJNxbfMqTkw3zr1zPeRaDy",authorities);
	 // return new User("foo","$2a$10$Cmu5BisoteEehMNz6CyMouNjyKeMK2kMJNxbfMqTkw3zr1zPeRaDy",new ArrayList<>());
	}

}
