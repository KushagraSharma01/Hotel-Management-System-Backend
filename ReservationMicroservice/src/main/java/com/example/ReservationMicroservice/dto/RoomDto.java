package com.example.ReservationMicroservice.dto;

import java.util.HashMap;
import java.util.List;

import jakarta.validation.constraints.Min;
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
<<<<<<< HEAD
	private Long roomNumber; 
=======
	private Long roomNumber;
>>>>>>> 7cd6b6941e801dc5b2cf4da7f8f3de81aaa09964
	private String roomType;
	private Long price;
	

}
