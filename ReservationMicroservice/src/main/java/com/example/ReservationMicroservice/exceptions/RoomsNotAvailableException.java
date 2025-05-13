package com.example.ReservationMicroservice.exceptions;

public class RoomsNotAvailableException extends Exception{
	
	public RoomsNotAvailableException(String message){
		super(message);
	}
	
}
