package com.example.security_project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //Here this annotation tells Don't go with default flow. Flow with what I mention here.
public class SecurityConfig {
	@Autowired
	private UserDetailsService userDetailService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(customizer -> customizer.disable());
		http.authorizeHttpRequests(request -> request
				.requestMatchers("register","login")// register & login no need to pass authentication in postman
				.permitAll()
				.anyRequest().authenticated());
		//http.formLogin(Customizer.withDefaults());//this line is for webpage formLogin
		http.httpBasic(Customizer.withDefaults());//this line is for postman
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilterBefore(, UsernamePasswordAuthenticationToken.class)
		return http.build();
		
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user1 = User
				.withDefaultPasswordEncoder()
				.username("Lakshmi")
				.password("L@123")
				.roles("USER")
				.build();
		
		UserDetails user2 = User
				.withDefaultPasswordEncoder()
				.username("Vadde")
				.password("V@123")
				.roles("ADMIN")
				.build();
		return new InMemoryUserDetailsManager(user1,user2);
				
		//return new InMemoryUserDetailsManager();
	}
	//DB Credentials
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		//provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());//we are storing using default once 
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		provider.setUserDetailsService(userDetailService);
		return provider;
		
		
	}
	//JWT
	@Bean
	public AuthenticationManager authenticationMagaer(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
		
	}

}
