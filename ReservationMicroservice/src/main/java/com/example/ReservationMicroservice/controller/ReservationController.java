package com.example.ReservationMicroservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.example.ReservationMicroservice.dto.ReservationDto;
import com.example.ReservationMicroservice.dto.RoomDto;
import com.example.ReservationMicroservice.service.ReservationService;
import com.example.ReservationMicroservice.service.RoomServiceProxy;

import feign.FeignException;
import feign.RetryableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
	
	@Autowired
	private ReservationService reservationService;
	
	@GetMapping("/getAllForGuest/{id}")
	public ResponseEntity<List<ReservationDto>> getAll(@PathVariable Long id) throws Exception{
		return reservationService.getAllForGuest(id);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<ReservationDto>> getAll() throws Exception{
		return reservationService.getAll();
	}
	 
	@GetMapping("/get/{id}")
	public ResponseEntity<ReservationDto> get(@PathVariable Long id) throws Exception{
		return reservationService.get(id);
	}

	@CircuitBreaker(name = "Create", fallbackMethod = "fallbackMethod")
	@PostMapping("/create/{id}")
	public ResponseEntity<ReservationDto> create(@Valid @PathVariable Long id, @Valid @RequestBody ReservationDto inDto) throws Exception{
		
		inDto.setGuestId(id);
		
		return reservationService.create(inDto);
	}
	
	public ResponseEntity<String> fallbackMethod(FeignException e){
		
		return new ResponseEntity<>("Room server is down currently. Please try again later", HttpStatus.NOT_FOUND);
		
	}
	
	@PutMapping("/edit/{id}")
	public ResponseEntity<ReservationDto> edit(@PathVariable Long id, @Valid @RequestBody ReservationDto newDto) throws Exception{
		return reservationService.edit(id, newDto);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws Exception{
		return reservationService.delete(id);
	}
	
	@GetMapping("/filter")
	public ResponseEntity<List<RoomDto>> getRooms(@RequestParam String checkInDate, @RequestParam String checkOutDate, @RequestParam String roomType) throws Exception{
		return reservationService.filter(checkInDate, checkOutDate, roomType);
	}
	
	
}
