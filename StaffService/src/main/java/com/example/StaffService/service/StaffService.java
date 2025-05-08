package com.example.StaffService.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.StaffService.dto.StaffDto;

public interface StaffService {

	public ResponseEntity<StaffDto> create(StaffDto staff) throws Exception;
	
	public ResponseEntity<StaffDto> get(Long id) throws Exception;
	
	public ResponseEntity<List<StaffDto>> getAll() throws Exception;
	
	public ResponseEntity<StaffDto> edit(Long id, StaffDto updatedStaff) throws Exception;
	
	public ResponseEntity<String> delete(Long id) throws Exception;
	
}
