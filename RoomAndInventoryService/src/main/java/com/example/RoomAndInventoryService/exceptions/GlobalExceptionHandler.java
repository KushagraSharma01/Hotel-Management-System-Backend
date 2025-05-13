package com.example.RoomAndInventoryService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = RoomAlreadyPresentException.class)
	public ResponseEntity<String> roomAlreadyPresent(RoomAlreadyPresentException exception){
		
		
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
		
	}
	
	@ExceptionHandler(value = RoomNotFoundException.class)
	public ResponseEntity<String> roomNotFound(RoomNotFoundException exception){
		
		
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
