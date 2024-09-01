package com.example.security_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import com.example.security_project.model.Users;

public interface UserRepo extends JpaRepository<Users, Integer>{

	Users findByUsername(String username);

}
