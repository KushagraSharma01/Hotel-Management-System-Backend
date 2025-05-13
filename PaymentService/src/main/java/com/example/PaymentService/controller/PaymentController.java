package com.example.PaymentService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PaymentService.dto.ProductRequest;
import com.example.PaymentService.dto.StripeResponse;
import com.example.PaymentService.service.PaymentService;

import feign.FeignException;
import feign.RetryableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/payments")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping("/checkout")
	public ResponseEntity<StripeResponse> checkout(@RequestBody ProductRequest product) throws Exception{
		return paymentService.checkout(product);
	}

	@GetMapping("/{confirm}/{id}")
	public ResponseEntity<String> confirm(@PathVariable String confirm, @PathVariable Long id) throws Exception {
		
		String statusFound = "";
		
		if(confirm.equals("success"))
			statusFound = "Success";
		else
			statusFound = "Failed";
		
		return paymentService.confirm(id, statusFound);
	}
	
	
//	public ResponseEntity<String> fallbackMethod(@PathVariable String confirm, @PathVariable Long id , Exception e) throws Exception{
//		
//		System.out.println("Exception is : "+e.getStackTrace());
//		
//		if(!(e instanceof FeignException) && !(e instanceof RetryableException) )
//			throw e;
//		
//		String statusFound = "";
//		
//		if(confirm.equals("success"))
//			statusFound = "Success";
//		else
//			statusFound = "Failed";
//		
//		return paymentService.fallbackMethod(statusFound, id);
//	}
//	

}
