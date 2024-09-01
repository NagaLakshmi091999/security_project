package com.example.security_project.student.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.security_project.model.Student;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class StudentController {
	
	private List<Student> students = new ArrayList<>(List.of(
			new Student(1,"Naga",98),
			new Student(2,"Lakshmi",90)
			));
	
	@GetMapping("/student")
	public List<Student> getStudents(){
		return students;
		
	}
	//why this getMapping means in springSecurity with csrf-token POST,PUT,DELETE API's are not working 
	@GetMapping("/csrf-token")
	public CsrfToken csrfToken(HttpServletRequest http){
		return (CsrfToken) http.getAttribute("_csrf");
		
	}
	//Before hitting this API get csrf-token and pass in this post API header
	@PostMapping()
	public Student createStudent(@RequestBody Student student) {
		return student;
		
	}

}
