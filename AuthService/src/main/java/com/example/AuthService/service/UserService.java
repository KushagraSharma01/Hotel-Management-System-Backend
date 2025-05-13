package com.example.AuthService.service;

import org.springframework.http.ResponseEntity;

import com.example.AuthService.dto.LoginDto;
import com.example.AuthService.dto.UserDto;
import com.example.AuthService.entity.Role;

public interface UserService {
	
	public ResponseEntity<UserDto> register(UserDto user, Role role) throws Exception;
	
	public ResponseEntity<String> login(LoginDto loginDto) throws Exception;

}
