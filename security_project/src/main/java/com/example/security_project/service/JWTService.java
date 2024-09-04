//package com.example.security_project.service;
//
//import java.security.Key;
//import java.security.NoSuchAlgorithmException;
//import java.util.Base64;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.io.Decoder;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//
//@Service
//public class JWTService<T> {

//	private String sceretKey = "";
//	
//	public JWTService() {
//		try {
//			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
//			SecretKey sk = keyGen.generateKey();
//			sceretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
//		} catch (NoSuchAlgorithmException e) {
//			throw new RuntimeException(e);
//		}
//	}
//	
//	public String generateToken(String username) {
//		// generate jwt token
//		Map<String,Object> claims = new HashMap<>();
//		return Jwts.builder()
//				.claims()
//				.add(claims)
//				.subject(username)
//				.issuedAt(new Date(System.currentTimeMillis()))
//				.expiration(new Date(System.currentTimeMillis() + 60 * 60 * 30))
//				.and()
//				.signWith(getKey())
//				.compact();
//		
//	}
//
//	private SecretKey getKey() {
//		// TODO Auto-generated method stub
//		byte[] keys = Decoders.BASE64.decode(sceretKey);
//		return Keys.hmacShaKeyFor(keys);
//	}
//
//	public String extractUserName(String token) {
//		// TODO Auto-generated method stub
//		return extractClaims(token, Claims::getSubject);
//	}
//
//	private <T> T extractClaims(String token, Function<Claims,T> claimsResolver) {
//		// TODO Auto-generated method stub
//		final Claims claims = extractAllClaims(token);
//		
//		return claimsResolver.apply(claims);
//	}
//
//	private Claims extractAllClaims(String token) {
//		// TODO Auto-generated method stub
//		return Jwts.parser().verifyWith(getKey()).build().parseClaimsJws(token).getBody();
//	}
//
//	public boolean UsernamePasswordAuthenticationToken(String token, UserDetails userDetails) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	public boolean validateToken(String token, UserDetails userDetails) {
//		// TODO Auto-generated method stub
//		final String userName = extractUserName(token);
//		
//		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
//	}
//
//	private boolean isTokenExpired(String token) {
//		// TODO Auto-generated method stub
//		return extractExpiration(token).before(new Date());
//	}
//
//	private Date extractExpiration(String token) {
//		return extractClaims(token, Claims::getSubject);
//	}
package com.example.security_project.service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService<T> {

	private String secretKey;

	public JWTService() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
			keyGen.init(256); // Specify the key size
			SecretKey sk = keyGen.generateKey();
			this.secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public String generateToken(String username) {
		// Generate JWT token
		Map<String, Object> claims = new HashMap<>();
		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 1 hour expiration
				.signWith(getKey()).compact();
	}

	private Key getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractUserName(String token) {
		return extractClaims(token, Claims::getSubject);
	}

	private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		final String userName = extractUserName(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaims(token, Claims::getExpiration);
	}
}
