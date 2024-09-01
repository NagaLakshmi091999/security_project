package com.example.security_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.security_project.model.Users;
import com.example.security_project.service.UserService;

@RestController
public class UserController {
	@Autowired
	UserService userService;
	@PostMapping("/register")
	public Users register(@RequestBody Users user) {
		return userService.register(user);
		
	}

}
