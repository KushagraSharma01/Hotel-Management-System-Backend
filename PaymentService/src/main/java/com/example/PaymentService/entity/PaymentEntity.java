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
@NoArgsConstructor
public class PaymentEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long amount;
	private String status;
	private String message;
	private String sessionId;
	
}
