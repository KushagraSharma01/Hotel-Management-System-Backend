package com.example.PaymentService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.PaymentService.dto.ProductRequest;
import com.example.PaymentService.dto.Receipt;
import com.example.PaymentService.dto.StripeResponse;
import com.example.PaymentService.entity.PaymentEntity;
import com.example.PaymentService.exceptions.PaymentNotFoundException;
import com.example.PaymentService.repository.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService{
	
	@Autowired
	private StripeService stripeService;
	
	@Autowired 
	private PaymentRepository paymentRepository;
	
	@Autowired
	private ReservationServiceProxy proxy;
	
	public ResponseEntity<StripeResponse> checkout(ProductRequest product){
		
		
		PaymentEntity newPayment = new PaymentEntity();
		
		//saving to get paymentId
		newPayment = paymentRepository.save(newPayment);
		
		StripeResponse response = stripeService.checkoutProducts(product, newPayment.getId());
		
		newPayment.setAmount(product.getAmount());
		newPayment.setMessage(response.getMessage());
		newPayment.setSessionId(response.getSessionId());
		newPayment.setStatus("Pending");
		
		//saving the payment in db with pending status
		paymentRepository.save(newPayment);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	} 
	
	public ResponseEntity<Receipt> confirm(Long id, String status) throws Exception{
		
		PaymentEntity foundPayment = paymentRepository.findByIdAndStatus(id, "Pending");
		
		if(foundPayment == null)
			throw new PaymentNotFoundException("No pending payments found with given payment Id");
		
		
		//make a call to reservation service to update the reservation status and getting a part of receipt
		Receipt response = proxy.confirm(foundPayment.getSessionId(), status).getBody();

			
		foundPayment.setStatus(status);
			
		//saving the success status in db
		paymentRepository.save(foundPayment);
		
		//sending the complete receipt to the user
		response.setAmount(foundPayment.getAmount());
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
	
	public ResponseEntity<String> fallbackMethod(String status, Long id){
		
		PaymentEntity foundPayment = paymentRepository.findByIdAndStatus(id, "Pending");
		
		//if the user made payment after the expired time (or the reservaiton service is down for some reason)
		//then refund will be given
		
		if(status.equals("Success"))
			foundPayment.setStatus("Refund");
		else
			foundPayment.setStatus(status);
		
		paymentRepository.save(foundPayment);
		
		status = "Gone for refund, Please do not make any further payments";
		
		return new ResponseEntity<>(status, HttpStatus.NOT_FOUND);
	}
	
	
}
