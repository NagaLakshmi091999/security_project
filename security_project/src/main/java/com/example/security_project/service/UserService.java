package com.example.security_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.security_project.model.Users;
import com.example.security_project.repository.UserRepo;

@Service
public class UserService {
	@Autowired
	private UserRepo repo;
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	JWTService jwtService;
	
	private BCryptPasswordEncoder encode = new BCryptPasswordEncoder(12);
	
	public Users register(Users user) {
		user.setPassword(encode.encode(user.getPassword()));
		return repo.save(user);
		
	}

	public String verifyUser(Users user) {
		// TODO Auto-generated method stub
		Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		if(authentication.isAuthenticated()) {
			//return "Success";//instead of return success here we need to generate JWT Token
			return jwtService.generateToken(user.getUsername());
		}
		return "fail";
	}

}
