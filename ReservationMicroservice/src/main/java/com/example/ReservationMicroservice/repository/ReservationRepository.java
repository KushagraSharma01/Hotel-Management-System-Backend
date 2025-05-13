package com.example.ReservationMicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ReservationMicroservice.entity.ReservationEntity;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long>{

	ReservationEntity findBySessionId(String sessionId);
	
}
