package com.example.StaffService.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.StaffService.dto.StaffDto;
import com.example.StaffService.service.StaffService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/staff")
public class StaffController {
	
	@Autowired
	private StaffService staffService;
	
	@PostMapping("/create")
	public ResponseEntity<StaffDto> create(@Valid @RequestBody StaffDto staff) throws Exception{
		return staffService.create(staff);
		
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<StaffDto> get(@PathVariable Long id) throws Exception{
		return staffService.get(id);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<StaffDto>> getAll()throws Exception{
		
		return staffService.getAll();
		
	}

	@PutMapping("/edit/{id}")
	public ResponseEntity<StaffDto> edit(@PathVariable Long id, @Valid @RequestBody StaffDto updatedStaff) throws Exception{
		
		return staffService.edit(id, updatedStaff);
		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws Exception{
		
		return staffService.delete(id);
	}
}
