package com.tracker.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.entity.User;
import com.tracker.repository.UserRepository;
import com.tracker.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	private UserRepository userRepository;
	
	
	@GetMapping("/health-checking")
	public String health_date() {
		System.out.println("ok");
//		org.springframework.security.core.Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		return "Ok";
	}
	
	@PutMapping
	public ResponseEntity<?> updateUser(@RequestBody User user)
	{
		System.out.println("1");
		org.springframework.security.core.Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		String userName=authentication.getName();
//		String userName=user.getUserName();
		System.out.println(userName);
		
		User userInDb=userService.findByUserName(userName);
		if(userInDb!=null)
		{
			userInDb.setUserName(user.getUserName());
			userInDb.setPassword(user.getPassword());
			userService.saveNewEntry(userInDb);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
