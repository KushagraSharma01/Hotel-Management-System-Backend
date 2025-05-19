package com.example.RoomAndInventoryService.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.RoomAndInventoryService.dto.RoomDto;

public interface RoomService {
	
	public ResponseEntity<List<RoomDto>> getAll() throws Exception;
	
	public ResponseEntity<RoomDto> create(RoomDto newRoom) throws Exception;

	public ResponseEntity<RoomDto> edit(Long id, RoomDto updatedRoom) throws Exception;
	
	public ResponseEntity<String> delete(Long id) throws Exception;
	
	public ResponseEntity<List<RoomDto>> filter(String roomType) throws Exception;
	
	

}



