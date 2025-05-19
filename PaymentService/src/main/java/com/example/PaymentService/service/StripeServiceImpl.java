package com.example.PaymentService.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.PaymentService.dto.ProductRequest;
import com.example.PaymentService.dto.StripeResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Service
public class StripeServiceImpl implements StripeService{

	//stripe - API
	//-> productName, amount, quantity, currency
	//-> return sessionId and url
	
	@Value("${stripe.secretKey}")
	private String secretKey;
	
	public StripeResponse checkoutProducts(ProductRequest productRequest, Long paymentId) {
		
		//here using the secret key
		Stripe.apiKey = secretKey;
		
		String id = paymentId.toString();
		
		//setting the name of the product data
		SessionCreateParams.LineItem.PriceData.ProductData productDate = SessionCreateParams.LineItem.PriceData.ProductData.builder()
					       .setName(productRequest.getName()).build();
		
		//setting the values of the price data
		SessionCreateParams.LineItem.PriceData priceDate = SessionCreateParams.LineItem.PriceData.builder()
						   .setCurrency(productRequest.getCurrency() == null ?"INR":productRequest.getCurrency())
						   .setUnitAmount(productRequest.getAmount()*100)
						   .setProductData(productDate)
						   .build();
						   
		//setting the values of line items
		SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
						   .setQuantity(productRequest.getQuantity())
						   .setPriceData(priceDate)
						   .build();
		
		//setting the values of SessionCreateParams
		SessionCreateParams params = SessionCreateParams.builder()
						   .setMode(SessionCreateParams.Mode.PAYMENT)
						   .setSuccessUrl("http://localhost:8087/payments/success/"+id)
						   .setCancelUrl("http://localhost:8087/payments/cancel/"+id)
						   .addLineItem(lineItem)
						   .setExpiresAt((new Date().getTime()+1000*30*60)/1000)
						   .build();
		
		//creating the checkout session
		Session session = null;
		
		try {
			
			session = Session.create(params);
			
			return new StripeResponse("Success", "Payment session created", session.getId(), session.getUrl());

		}
		catch(StripeException e) {
			
			System.out.println(e.getMessage());
			
			return new StripeResponse("Failure", "Payment session created", session.getId(), session.getUrl());

		}
		
				
		
	}
}
