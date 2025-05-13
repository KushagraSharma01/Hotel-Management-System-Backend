package com.example.ReservationMicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StripeResponse {

	private String status;
<<<<<<< HEAD
	private String message; 
=======
	private String message;
>>>>>>> 7cd6b6941e801dc5b2cf4da7f8f3de81aaa09964
	private String sessionId;
	private String sessionUrl;
	
}
