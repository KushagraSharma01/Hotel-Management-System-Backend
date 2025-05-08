package com.example.RoomAndInventoryService.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
	
	private List<Long> roomNumbers;
	
	private String checkInDate;
	private String checkOutDate;
	
	private Long guestId;
	
	
}
