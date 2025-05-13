package com.example.PaymentService.service;

import com.example.PaymentService.dto.ProductRequest;
import com.example.PaymentService.dto.StripeResponse;

public interface StripeService {
	
	public StripeResponse checkoutProducts(ProductRequest productRequest, Long paymentId);

}
