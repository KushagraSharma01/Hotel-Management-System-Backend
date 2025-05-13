package com.example.AuthService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AuthService.dto.LoginDto;
import com.example.AuthService.dto.UserDto;
import com.example.AuthService.entity.Role;
import com.example.AuthService.exceptions.InvalidRoleException;
import com.example.AuthService.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private AuthenticationManager authenticationManager;
	
	
	@PostMapping("/admin/register/{role}")
	public ResponseEntity<UserDto> registerAdmin(@Valid @RequestBody UserDto user, @PathVariable String role) throws Exception{
		
		Role foundRole = null;
		
		if(role.toUpperCase().equals("ADMIN"))
			foundRole = Role.ADMIN;
		
		else if(role.toUpperCase().equals("MANAGER"))
			foundRole = Role.MANAGER;
		
		else if(role.toUpperCase().equals("RECEPTIONIST"))
			foundRole = Role.RECEPTIONIST;
		
		else 
			throw new InvalidRoleException("Invalid role is given, Role can only be ADMIN, MANAGER AND RECEPTIONIST");
		
		return userService.register(user, foundRole);
		
	}
	
	@PostMapping("/manager/register/receptionist")
	public ResponseEntity<UserDto> registerManager(@Valid @RequestBody UserDto user) throws Exception{
		
		return userService.register(user, Role.RECEPTIONIST);
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> loginManager(@Valid @RequestBody LoginDto loginDto) throws Exception{
		
		//authentication through authentication manager
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
		
		return userService.login(loginDto);
	}
	
	

}
