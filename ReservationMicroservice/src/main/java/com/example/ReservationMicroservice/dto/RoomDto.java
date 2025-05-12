package com.example.ReservationMicroservice.dto;

import java.util.HashMap;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
private Long id;
	
	private Long roomNumber;
	
	private List<Long> guestIds;
	
	private HashMap<String, String> dates;
	private String roomType;
	
	

}
