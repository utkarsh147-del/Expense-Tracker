package com.tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.tracker.entity.User;
import com.tracker.repository.UserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User userDetails=userRepository.findByuserName(username);
		System.out.println("name "+userDetails.getUserName());
		System.out.println("password "+userDetails.getPassword() );
		
		if(userDetails!=null)
		{
			
			return org.springframework.security.core.userdetails.User.builder().username(userDetails.getUserName())
			.password(userDetails.getPassword())
			.roles(userDetails.getRoles().toArray(new String[0]))
			.build();
			
		}
		throw new UsernameNotFoundException("User not found with username: "+username);
		
		}
		
	
}
