package com.example.RoomAndInventoryService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.RoomAndInventoryService.dto.BookingDto;
import com.example.RoomAndInventoryService.dto.RoomDto;
import com.example.RoomAndInventoryService.service.RoomService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rooms")
public class RoomController {
	
	@Autowired
	private RoomService roomService; 
	
	@GetMapping("/getAll")
	public ResponseEntity<List<RoomDto>> getAll() throws Exception{
		return roomService.getAll();
	}
	
	@PostMapping("/create")
	public ResponseEntity<RoomDto> create(@Valid @RequestBody RoomDto newRoom) throws Exception{
		return roomService.create(newRoom);
	}
	
	@PutMapping("/edit/{id}")
	public ResponseEntity<RoomDto> edit(@PathVariable Long id, @Valid @RequestBody RoomDto updatedRoom) throws Exception{
		return roomService.edit(id, updatedRoom);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws Exception{
		return roomService.delete(id);
	}
	
	@GetMapping("/filter")
	public ResponseEntity<List<RoomDto>> filter(@RequestParam String roomType)throws Exception{
		return roomService.filter(roomType);
	}


}
