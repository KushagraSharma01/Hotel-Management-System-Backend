package com.example.PaymentService.service;

import org.springframework.http.ResponseEntity;

import com.example.PaymentService.dto.ProductRequest;
import com.example.PaymentService.dto.Receipt;
import com.example.PaymentService.dto.StripeResponse;

public interface PaymentService {
	
	public ResponseEntity<StripeResponse> checkout(ProductRequest product) throws Exception;
	
	public ResponseEntity<Receipt> confirm(Long id, String status) throws Exception;
	
	public ResponseEntity<String> fallbackMethod(String status, Long id);
	
}
