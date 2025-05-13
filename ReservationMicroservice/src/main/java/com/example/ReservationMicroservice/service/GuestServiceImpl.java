package com.example.ReservationMicroservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.ReservationMicroservice.dto.GuestDto;
import com.example.ReservationMicroservice.entity.GuestEntity;
import com.example.ReservationMicroservice.exceptions.GuestAlreadyPresentException;
import com.example.ReservationMicroservice.exceptions.GuestNotFoundException;
import com.example.ReservationMicroservice.repository.GuestRepository;

@Service
public class GuestServiceImpl implements GuestService{

	@Autowired
	private GuestRepository guestRepository;
	
	public ResponseEntity<GuestDto> get(Long id) throws Exception{
		
		GuestEntity foundEntity = guestRepository.findById(id).orElseThrow( () -> new GuestNotFoundException("No guest was found with the given id"));
		
		GuestDto resDto = new GuestDto(foundEntity.getId(), foundEntity.getMemberCode(), foundEntity.getPhoneNumber(), foundEntity.getCompany(), foundEntity.getName(), foundEntity.getEmail(), foundEntity.getGender(), foundEntity.getAddress() );
		
		return new ResponseEntity<>(resDto, HttpStatus.OK);
	}
	
	public ResponseEntity<GuestDto> create(GuestDto guest) throws Exception{
		
		if(guestRepository.findByEmail(guest.getEmail()) != null)
			throw new GuestAlreadyPresentException("Guest with the given email already exists");
		
		GuestEntity newGuest = new GuestEntity(guest.getMemberCode(), guest.getPhoneNumber(), guest.getCompany(), guest.getName(), guest.getEmail(), guest.getGender(), guest.getAddress());
		
		newGuest = guestRepository.save(newGuest);
		
		GuestDto resDto = new GuestDto( newGuest.getId(), guest.getMemberCode(), guest.getPhoneNumber(), guest.getCompany(), guest.getName(), guest.getEmail(), guest.getGender(), guest.getAddress());
		
		return new ResponseEntity<>(resDto, HttpStatus.OK);
	}
	
	public ResponseEntity<GuestDto> edit(Long id, GuestDto guest) throws Exception{
		
		GuestEntity foundEntity = guestRepository.findById(id).orElseThrow( () -> new GuestNotFoundException("No guest was found with the given id"));;
		
		foundEntity.setAddress(guest.getAddress());
		foundEntity.setCompany(guest.getCompany());
		foundEntity.setEmail(guest.getEmail());
		foundEntity.setGender(guest.getGender());
		foundEntity.setMemberCode(guest.getMemberCode());
		foundEntity.setName(guest.getName());
		foundEntity.setPhoneNumber(guest.getPhoneNumber());
		
		foundEntity = guestRepository.save(foundEntity);
		
		GuestDto resDto = new GuestDto(foundEntity.getId(), foundEntity.getMemberCode(), foundEntity.getPhoneNumber(), foundEntity.getCompany(), foundEntity.getName(), foundEntity.getEmail(), foundEntity.getGender(), foundEntity.getAddress() );

		return new ResponseEntity<>(resDto, HttpStatus.OK);
		
		
	}
	
	public ResponseEntity<String> delete(Long id) throws Exception{
		
		GuestEntity foundGuest = guestRepository.findById(id).orElseThrow( () -> new GuestNotFoundException("No guest was found with the given id"));;
		
		guestRepository.deleteById(id);
		
		return new ResponseEntity<>("guest deleted", HttpStatus.OK);
		
	}
	
}
