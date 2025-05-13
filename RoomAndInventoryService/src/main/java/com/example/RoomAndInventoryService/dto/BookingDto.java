package com.example.RoomAndInventoryService.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
<<<<<<< HEAD
public class BookingDto { 
=======
public class BookingDto {
>>>>>>> 7cd6b6941e801dc5b2cf4da7f8f3de81aaa09964
	
	private List<Long> roomNumbers;
	
	private String checkInDate;
	private String checkOutDate;
	
	private Long guestId;
	
	
}
