package com.example.RoomAndInventoryService.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.RoomAndInventoryService.dto.BookingDto;
import com.example.RoomAndInventoryService.dto.RoomDto;

public interface RoomService {
	
	public ResponseEntity<List<RoomDto>> getAll() throws Exception;
	
	public ResponseEntity<RoomDto> create(RoomDto newRoom) throws Exception;

	public ResponseEntity<RoomDto> edit(Long id, RoomDto updatedRoom) throws Exception;
	
	public ResponseEntity<String> delete(Long id) throws Exception;
	
	public ResponseEntity<List<RoomDto>> filter(String checkInDate, String checkOutDate, String roomType) throws Exception;
	
	public ResponseEntity<List<RoomDto>> bookRooms(BookingDto bookDto) throws Exception;

}



