package com.example.PaymentService.exceptions;

public class PaymentAlreadyPendingException extends Exception{
	
	public PaymentAlreadyPendingException(String message) {
		super(message);
	}

}
