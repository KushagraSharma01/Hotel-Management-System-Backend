package com.example.ReservationMicroservice.exceptions;

import java.text.ParseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = ReservationAlreadyPresentException.class)
	public ResponseEntity<String> reservationAlreadyPresent(ReservationAlreadyPresentException exception){
		
		
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
		
	}
	
	@ExceptionHandler(value = ReservationNotFoundException.class) 
	public ResponseEntity<String> reservationNotFound(ReservationNotFoundException exception){
		
		
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(value = GuestAlreadyPresentException.class)
	public ResponseEntity<String> guestAlreadyPresent(GuestAlreadyPresentException exception){
		
		
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
		
	}
	
	@ExceptionHandler(value = GuestNotFoundException.class)
	public ResponseEntity<String> guestNotFound(GuestNotFoundException exception){
		
		
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
		
	}
	
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<String> methodArgumentNotValid(MethodArgumentNotValidException exception){
		
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(value = ParseException.class)
	public ResponseEntity<String> parseException( ParseException e){
		
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
				
	}
	
	@ExceptionHandler(value = RoomsNotAvailableException.class)
	public ResponseEntity<String> roomsNotAvailable(RoomsNotAvailableException e){
		
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = ServiceDownException.class)
	public ResponseEntity<String> roomServiceDown(ServiceDownException e){
		
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<String> exception(Exception e){
		
		e.printStackTrace();
		
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
				
	}
	
	
	
	
}
