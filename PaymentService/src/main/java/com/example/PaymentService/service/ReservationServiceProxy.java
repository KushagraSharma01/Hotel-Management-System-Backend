package com.example.PaymentService.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "ReservationMicroservice")
public interface ReservationServiceProxy {
	
	@PostMapping("/reservations/confirm/{sessionId}/{status}")
	public ResponseEntity<String> confirm(@PathVariable String sessionId,@PathVariable String status) throws Exception;

}
