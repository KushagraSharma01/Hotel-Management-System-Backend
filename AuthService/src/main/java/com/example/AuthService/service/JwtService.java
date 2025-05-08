package com.example.AuthService.service;

import java.security.Key;
import java.util.function.Function;

import com.example.AuthService.entity.UserEntity;

import io.jsonwebtoken.Claims;

public interface JwtService {
	
	
	public String generateToken(UserEntity userDetails);
	
	public Key getSiginKey();
	
	

	
	
}
