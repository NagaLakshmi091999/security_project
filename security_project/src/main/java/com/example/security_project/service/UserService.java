package com.example.security_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.security_project.model.Users;
import com.example.security_project.repository.UserRepo;

@Service
public class UserService {
	@Autowired
	private UserRepo repo;
	
	private BCryptPasswordEncoder encode = new BCryptPasswordEncoder(12);
	
	public Users register(Users user) {
		user.setPassword(encode.encode(user.getPassword()));
		return repo.save(user);
		
	}

}
