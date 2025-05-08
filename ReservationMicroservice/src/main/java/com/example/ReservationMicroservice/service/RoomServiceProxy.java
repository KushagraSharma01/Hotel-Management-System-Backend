package com.example.ReservationMicroservice.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ReservationMicroservice.dto.BookingDto;
import com.example.ReservationMicroservice.dto.RoomDto;

//add comments to make it more clear for the user

//@FeignClient(name = "RoomAndInventoryService", url = "localhost:8082")	//does not need naming server to make request

@FeignClient(name = "RoomAndInventoryService")	//need loadbalancer of naming server to make request to available service
public interface RoomServiceProxy {
	
	@GetMapping("/rooms/filter")
	public ResponseEntity<List<RoomDto>> filter(@RequestParam String checkInDate, @RequestParam String checkOutDate, @RequestParam String roomType);

	@PostMapping("/rooms/bookRooms")
	public ResponseEntity<List<RoomDto>> book(@RequestBody BookingDto bookDto);
	
}
