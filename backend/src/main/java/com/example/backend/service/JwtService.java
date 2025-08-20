package com.example.backend.service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
    @Value("${security.jwt.secret-key}")
	private String secretKey;
	 
    @Value("${security.jwt.expiration-time}")
	private long jwtExpiration;
	
	public long getExpirationTime() {
		 return jwtExpiration;
	 }
	 public String extractUsername(String token) {
		 return extractClaims(token, Claims::getSubject);
	 }
	 
	 public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
		 final Claims claims = extractAllClaims(token);
		 return claimsResolver.apply(claims);
	 }
	 
	 private Claims extractAllClaims(String token) {
		 
		 return Jwts.parserBuilder()
				 .setSigningKey(getSignKey())
				 .build()
				 .parseClaimsJws(token)
				 .getBody();

	 }
	 
	 //generate token with extra claims
	 
	 public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		 return buildToken(extraClaims, userDetails, jwtExpiration);
	 }
	 
	 public String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
		 
		 return Jwts.builder()
				 .setClaims(extraClaims)
				 .setSubject(userDetails.getUsername())
				 .setIssuedAt(new Date(System.currentTimeMillis()))
				 .setExpiration(new Date(System.currentTimeMillis()+ expiration))
				 .signWith(getSignKey(), SignatureAlgorithm.HS256)
				 .compact();
	 }
	 
	 //check if the token is valid or not
	 public boolean isTokenValid(String token, UserDetails userDetails) {
		 final String userName = extractUsername(token);
		 
		 return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	 }
	 
	 private boolean isTokenExpired(String token) {
		 return extractExpiration(token).before(new Date());
		 
	 }
	 
	 private Date extractExpiration(String token) {
		 return extractClaims(token, Claims::getExpiration);
	 }
	 
	 
	 public Key getSignKey() {
		 byte [] keyBytes = Decoders.BASE64.decode(secretKey);
		 return Keys.hmacShaKeyFor(keyBytes);
	 }
}