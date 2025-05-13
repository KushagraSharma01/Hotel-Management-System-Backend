package com.example.ReservationMicroservice.service;

import org.springframework.http.ResponseEntity;

import com.example.ReservationMicroservice.dto.GuestDto;

public interface GuestService {

	public ResponseEntity<GuestDto> get(Long id) throws Exception;
	
	public ResponseEntity<GuestDto> create(GuestDto guest) throws Exception;
	
	public ResponseEntity<GuestDto> edit(Long id, GuestDto guest) throws Exception;
	
	public ResponseEntity<String> delete(Long id) throws Exception;
	
}
