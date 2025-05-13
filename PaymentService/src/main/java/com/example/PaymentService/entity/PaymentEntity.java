package com.example.PaymentService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments")
@Data
@AllArgsConstructor
<<<<<<< HEAD
@NoArgsConstructor 
=======
@NoArgsConstructor
>>>>>>> 7cd6b6941e801dc5b2cf4da7f8f3de81aaa09964
public class PaymentEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long amount;
	private String status;
	private String message;
	private String sessionId;
	
}
