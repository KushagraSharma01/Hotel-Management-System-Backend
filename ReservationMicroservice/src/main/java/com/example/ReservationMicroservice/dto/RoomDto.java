package com.example.ReservationMicroservice.dto;

import java.util.HashMap;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
private Long id;
	
	private Long roomNumber;
	
	private List<Long> guestIds;
	
	private HashMap<String, String> dates;
	private String roomType;
	
	
	public RoomDto(List<Long> guestIds, Long roomNumber, HashMap<String, String> dates, String roomType) {
		super();
		this.guestIds = guestIds;
		this.roomNumber = roomNumber;
		this.dates = dates;
		this.roomType = roomType;
	}
	
	

}
