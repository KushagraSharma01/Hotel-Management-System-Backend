package com.example.AuthService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = UserAlreadyPresentException.class)
	public ResponseEntity<String> userAlreadyPresent(UserAlreadyPresentException exception){
		
		
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
		
	}
	
	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<String> userNotFound(UserNotFoundException exception){
		
		
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<String> methodArgumentNotValid(MethodArgumentNotValidException exception){
		
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(value = InvalidRoleException.class)
	public ResponseEntity<String> invalidRole(InvalidRoleException e){
		
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<String> exception(Exception e){
		
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
				
	}
	
}
