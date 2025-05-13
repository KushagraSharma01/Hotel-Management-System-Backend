package com.example.ReservationMicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ReservationMicroservice.dto.GuestDto;
import com.example.ReservationMicroservice.service.GuestService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/guest")
public class GuestController {
	
	@Autowired
	private GuestService guestService;

	@GetMapping("/get/{id}")
	public ResponseEntity<GuestDto> get(@PathVariable Long id) throws Exception{
		return guestService.get(id);
	}
	
	@PostMapping("/create")
	public ResponseEntity<GuestDto> create(@Valid @RequestBody GuestDto guest) throws Exception{
		return guestService.create(guest);
	}
	
	@PutMapping("/edit/{id}")
	public ResponseEntity<GuestDto> edit(@Valid @RequestBody GuestDto guest, @PathVariable Long id) throws Exception{
		return guestService.edit(id, guest);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws Exception{
		return guestService.delete(id);
	}
	
	
}
