package com.example.ReservationMicroservice.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.ReservationMicroservice.dto.ReservationDto;
import com.example.ReservationMicroservice.dto.RoomDto;

public interface ReservationService {

	public ResponseEntity<ReservationDto> create(ReservationDto inDto) throws Exception;
	
	public ResponseEntity<List<ReservationDto>> getAllForGuest(Long id) throws Exception;
	
	public ResponseEntity<List<ReservationDto>> getAll() throws Exception;
	
	public ResponseEntity<ReservationDto> get(Long id) throws Exception;
	
	public ResponseEntity<ReservationDto> edit(Long id, ReservationDto newDto) throws Exception;
	
	public ResponseEntity<String> delete(Long id) throws Exception;
	
	public ResponseEntity<List<RoomDto>> filter(String checkInDate, String checkOutDate, String roomType) throws Exception;
	
}
