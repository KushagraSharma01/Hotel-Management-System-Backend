package com.example.PaymentService.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {
	
	private Long guestId;
	private List<Long> roomNumbers;
	private String sessionId;
	private Long amount;

}
