package com.example.AuthService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.AuthService.dto.LoginDto;
import com.example.AuthService.dto.UserDto;
import com.example.AuthService.entity.Role;
import com.example.AuthService.entity.UserEntity;
import com.example.AuthService.exceptions.UserAlreadyPresentException;
import com.example.AuthService.exceptions.UserNotFoundException;
import com.example.AuthService.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtService jwtService;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public ResponseEntity<UserDto> register(UserDto user, Role role) throws Exception{
		
		//check for already present user
		if(userRepository.findByEmailAndRole(user.getEmail(), role) != null)
			throw new UserAlreadyPresentException("User already registered with the given email and role");
		
		UserEntity newUser = new UserEntity(user.getFirstName(), user.getLastName(), user.getEmail(), encoder.encode(user.getPassword()));
		
		newUser.setRole(role);
		
		newUser = userRepository.save(newUser);
		
		UserDto resDto = new UserDto(newUser.getId(), newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(), newUser.getPassword(), newUser.getRole());
		
		return new ResponseEntity<>(resDto, HttpStatus.OK);
		
	}
	
	public ResponseEntity<String> login(LoginDto loginDto) throws Exception{
		
		//check if user is present or not
		if(userRepository.findByEmail(loginDto.getEmail()) == null)
			throw new UserNotFoundException("User not found with the given email, please register first");
		
		UserEntity foundUser = userRepository.findByEmail(loginDto.getEmail());
		
		
		String token = jwtService.generateToken(foundUser);
		
		return new ResponseEntity<>("Token : "+token, HttpStatus.OK);
		
	}

}
