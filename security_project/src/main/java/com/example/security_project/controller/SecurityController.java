package com.example.security_project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {
	
	@GetMapping()
	public String getWelcomeMessage() {
		return "Hii Welcome to our website";
	}

}
