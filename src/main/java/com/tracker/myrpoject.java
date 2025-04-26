package com.tracker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class myrpoject {
	
	@GetMapping("abc")
	public String sayHello() {
		return "Hello";
	}

}
