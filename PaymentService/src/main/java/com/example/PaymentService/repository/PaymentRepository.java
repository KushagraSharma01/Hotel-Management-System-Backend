package com.example.PaymentService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.PaymentService.entity.PaymentEntity;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long>{

	PaymentEntity findByIdAndStatus(Long Id, String status);
	
}
