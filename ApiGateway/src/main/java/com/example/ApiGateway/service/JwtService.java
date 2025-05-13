package com.example.ApiGateway.service;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.example.ApiGateway.entity.Role;

import io.jsonwebtoken.Claims;

public interface JwtService {
	
	public String extractUserName(String token);
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) ;
		
	public Claims extractAllClaims(String token) ;
		
	public boolean isTokenValid(String token, Role ROLE);
	
	public boolean isTokenExpired(String token);


}
