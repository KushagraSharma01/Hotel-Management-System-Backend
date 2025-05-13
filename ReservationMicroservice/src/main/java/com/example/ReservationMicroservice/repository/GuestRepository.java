package com.example.ReservationMicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ReservationMicroservice.entity.GuestEntity;

@Repository
public interface GuestRepository extends JpaRepository<GuestEntity, Long>{

	public GuestEntity findByEmail(String email);
	
}
