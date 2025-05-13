package com.example.StaffService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.StaffService.dto.StaffDto;
import com.example.StaffService.entity.StaffEntity;

@Repository
public interface StaffRepository extends JpaRepository<StaffEntity, Long>{

	public StaffEntity findByEmail(String email);
	
}
