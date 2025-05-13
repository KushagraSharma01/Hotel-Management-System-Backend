package com.example.PaymentService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(PaymentAlreadyPendingException.class)
	public ResponseEntity<String> paymentAlreadyPendingException(PaymentAlreadyPendingException e){
		
		return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		
	}
	
	@ExceptionHandler(PaymentNotFoundException.class)
	public ResponseEntity<String> paymentNotFoundException(PaymentNotFoundException e){
		
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		
	}

}
