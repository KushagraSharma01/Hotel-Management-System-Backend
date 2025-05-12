package com.example.RoomAndInventoryService.dto;

import java.util.HashMap;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
	
	@NotNull(message = "roomNumber cannot be null")
	private Long roomNumber;
	
	@NotNull(message = "guestIds cannot be null")
	private List<Long> guestIds;
	
	private HashMap<String, String> dates;
	
	@NotNull(message = "roomType cannot be null")
	@Pattern(regexp = "^(AC)|(Non-AC)$", message = "Room type can either AC or Non-AC")
	private String roomType;
	
	
	

}
