package com.example.RoomAndInventoryService.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
	
	private Long id;
	
	@NotNull(message = "roomNumber cannot be null")
	private Long roomNumber;
	
	@NotNull(message = "roomType cannot be null")
	@Pattern(regexp = "^(AC)|(Non-AC)$", message = "Room type can either AC or Non-AC")
	private String roomType;
	
	@NotNull(message = "room price cannot be null")
	@Min(1)
	private Long price;
	

}
