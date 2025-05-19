package com.example.RoomAndInventoryService.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto { 
	
	private List<Long> roomNumbers;
	
	private String checkInDate;
	private String checkOutDate;
	
	private Long guestId;
	
	
}
