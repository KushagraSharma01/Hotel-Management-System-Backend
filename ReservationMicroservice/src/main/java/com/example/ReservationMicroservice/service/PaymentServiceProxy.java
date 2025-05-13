package com.example.ReservationMicroservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.ReservationMicroservice.dto.ProductRequest;
import com.example.ReservationMicroservice.dto.StripeResponse;


@FeignClient(name = "PaymentService")
public interface PaymentServiceProxy {

	@PostMapping("/payments/checkout")
	public ResponseEntity<StripeResponse> checkout(@RequestBody ProductRequest product) throws Exception;

}
