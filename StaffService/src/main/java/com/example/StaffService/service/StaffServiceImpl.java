package com.example.StaffService.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.StaffService.dto.StaffDto;
import com.example.StaffService.entity.StaffEntity;
import com.example.StaffService.exceptions.StaffAlreadyPresentException;
import com.example.StaffService.exceptions.StaffNotFoundException;
import com.example.StaffService.repository.StaffRepository;

@Service
public class StaffServiceImpl implements StaffService{

	@Autowired
	private StaffRepository staffRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	
	public ResponseEntity<StaffDto> create(StaffDto staff) throws Exception{
		
		//check for already present staff
		if(staffRepository.findByEmail(staff.getEmail()) != null)
			throw new StaffAlreadyPresentException("Staff cannot be created with the already present email again");
		
		StaffEntity newStaff = mapper.map(staff, StaffEntity.class);
		
		newStaff = staffRepository.save(newStaff);
		
		StaffDto resDto = mapper.map(newStaff, StaffDto.class);
		
		ResponseEntity<StaffDto> response = new ResponseEntity<>(resDto, HttpStatus.OK);
		
		return response;
	}
	
	public ResponseEntity<StaffDto> get(Long id) throws Exception{
		
		StaffEntity foundStaff = staffRepository.findById(id).orElseThrow(()-> new StaffNotFoundException("Staff with the given id was not found"));
		
		StaffDto resDto = mapper.map(foundStaff, StaffDto.class);
		
		return new ResponseEntity<>(resDto, HttpStatus.OK);
		
		
	}
	
	public ResponseEntity<List<StaffDto>> getAll() throws Exception{
		
		List<StaffDto> foundStaffs = staffRepository.findAll().stream()
															  .map(staff -> mapper.map(staff, StaffDto.class))
															  .collect(Collectors.toList());
		
		if(foundStaffs.isEmpty())
			throw new StaffNotFoundException("No staff was found in the entire table");
		
		return new ResponseEntity<>(foundStaffs, HttpStatus.OK);
		
	}
	
	public ResponseEntity<StaffDto> edit(Long id, StaffDto updatedStaff) throws Exception{
		
		StaffEntity foundStaff = staffRepository.findById(id).orElseThrow(()-> new StaffNotFoundException("Staff with the given id was not found"));
		
		foundStaff.setAddress(updatedStaff.getAddress());
		foundStaff.setAge(updatedStaff.getAge());
		foundStaff.setCode(updatedStaff.getCode());
		foundStaff.setEmail(updatedStaff.getEmail());
		foundStaff.setName(updatedStaff.getName());
		foundStaff.setNic(updatedStaff.getNic());
		foundStaff.setOccupation(updatedStaff.getOccupation());
		foundStaff.setSalary(updatedStaff.getSalary());
		
		foundStaff = staffRepository.save(foundStaff);
		
		StaffDto resDto = mapper.map(foundStaff, StaffDto.class);
		
		return new ResponseEntity<>(resDto, HttpStatus.OK);
		
	}
	
	public ResponseEntity<String> delete(Long id) throws Exception{
		
		StaffEntity foundStaff = staffRepository.findById(id).orElseThrow(()-> new StaffNotFoundException("Staff with the given id was not found"));

		staffRepository.delete(foundStaff);
		
		return new ResponseEntity<>("Staff Deleted Successfully", HttpStatus.OK);
		
	}
	
	
	
}
