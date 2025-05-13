package com.example.StaffService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = StaffAlreadyPresentException.class)
	public ResponseEntity<String> staffAlreadyPresent(StaffAlreadyPresentException exception){
		
		
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
		
	}
	
	@ExceptionHandler(value = StaffNotFoundException.class)
	public ResponseEntity<String> staffNotFound(StaffNotFoundException exception){
		
		
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<String> methodArgumentNotValid(MethodArgumentNotValidException exception){
		
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<String> exception(Exception e){
		
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
				
	}
	
	
}
