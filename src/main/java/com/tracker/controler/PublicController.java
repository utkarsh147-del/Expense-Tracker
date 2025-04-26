package com.tracker.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.entity.User;
import com.tracker.service.UserService;

@RestController
@RequestMapping("/public")
public class PublicController {
	
	@Autowired
	private UserService userService;
	
		
	@GetMapping("/health-check")
	public String health() {
		System.out.println("ok");
		return "Ok";
	}
		
		@PostMapping("/create-user")
		public void createUser(@RequestBody User user) {
			System.out.println("Saving user: " + user.getUserName());	
			userService.saveNewEntry(user);
		}
		
		@PostMapping("/create-admin-user")
		public void createUser1(@RequestBody User user) {
			
			userService.saveAdmin(user);
		}
		
		@PostMapping("/login")
		public ResponseEntity<String> login(@RequestBody User request) {
			
			System.out.println("Login attempt for: " + request.getUserName()); // Debug
			System.out.println("Login attempt for: " + request.getPassword()); // Debug
		    if (userService.authenticate(request.getUserName(), request.getPassword())) {
		        System.out.println("Authentication successful for: " + request.getUserName()); // Debug
		        return ResponseEntity.ok(request.getUserName()); // Token as username
		    }
		    return ResponseEntity.status(401).body("Invalid credentials");
		}
	

}

class LoginRequest {
    private String userName;
    private String password;

    public String getUsername() { return userName; }
    public void setUsername(String username) { this.userName = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
