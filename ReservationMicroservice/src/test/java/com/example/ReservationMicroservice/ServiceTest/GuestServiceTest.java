package com.example.ReservationMicroservice.ServiceTest;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.example.ReservationMicroservice.dto.GuestDto;
import com.example.ReservationMicroservice.entity.GuestEntity;
import com.example.ReservationMicroservice.repository.GuestRepository;
import com.example.ReservationMicroservice.service.GuestServiceImpl;

@ExtendWith(MockitoExtension.class)
public class GuestServiceTest {

	@Mock
	GuestRepository guestRepository;
	
	@InjectMocks
	GuestServiceImpl guestServiceImpl; 
	
	@BeforeEach
	void setup() {
		GuestEntity newEntity = new GuestEntity("ABC123", "976543210", "CapGemini", "Kushagra Sharma", "kush@gmail.com", "Male", "Agra");
		
		Mockito.lenient().when(guestRepository.save(any(GuestEntity.class))).thenReturn(newEntity);
		
		Mockito.lenient().when(guestRepository.findById(1L)).thenReturn(Optional.of(newEntity));
		
	
	}
	
	@Test
	@DisplayName("createTest")
	void createTest() throws Exception{
		
		GuestDto newDto = new GuestDto(1L, "ABC123", "976543210", "CapGemini", "Kushagra Sharma", "kush@gmail.com", "Male", "Agra");
		
		//checking
		ResponseEntity<GuestDto> response = guestServiceImpl.create(newDto);
		
		GuestDto resDto = response.getBody();
		
		assertAll(()->assertTrue(newDto.getAddress().equals(resDto.getAddress())),
				  ()->assertTrue(newDto.getCompany().equals(resDto.getCompany())), 
				  ()->assertTrue(newDto.getEmail().equals(resDto.getEmail())),
				  ()->assertTrue(newDto.getGender().equals(resDto.getGender())),
				  ()->assertTrue(newDto.getMemberCode().equals(resDto.getMemberCode())), 
				  ()->assertTrue(newDto.getPhoneNumber().equals(resDto.getPhoneNumber())), 
				  ()->assertTrue(newDto.getName().equals(resDto.getName())));
		
		System.out.println("Name of the response guest is : "+resDto.getName());
	}
	
	@Test
	@DisplayName("getTest")
	void getTest() throws Exception{
		
		GuestDto newDto = new GuestDto(1L, "ABC123", "976543210", "CapGemini", "Kushagra Sharma", "kush@gmail.com", "Male", "Agra");
		
		guestServiceImpl.create(newDto);  
		
		//checking
		ResponseEntity<GuestDto> response = guestServiceImpl.get(1L);
		
		GuestDto resDto = response.getBody();
		
		assertAll(()->assertTrue(newDto.getAddress().equals(resDto.getAddress())),
				  ()->assertTrue(newDto.getCompany().equals(resDto.getCompany())), 
				  ()->assertTrue(newDto.getEmail().equals(resDto.getEmail())),
				  ()->assertTrue(newDto.getGender().equals(resDto.getGender())),
				  ()->assertTrue(newDto.getMemberCode().equals(resDto.getMemberCode())), 
				  ()->assertTrue(newDto.getPhoneNumber().equals(resDto.getPhoneNumber())), 
				  ()->assertTrue(newDto.getName().equals(resDto.getName())));
		
		System.out.println("Name of the response guest is : "+resDto.getName());
		
	}
	
	@Test
	@DisplayName("editTest")
	void editTest() throws Exception{
		
		//creating a newDto
		GuestDto newDto = new GuestDto(1L, "ABC123", "976543210", "CapGemini", "Kushagra Sharma", "kush@gmail.com", "Male", "Agra");

		guestServiceImpl.create(newDto);  
		

		GuestDto updatedDto = new GuestDto(1L, "ABC123", "976543210", "CapGemini", "Vishesh Sharma", "vishesh@gmail.com", "Male", "Agra");
		
		//mocking the response for the updatedDto
		Mockito.lenient().when(guestRepository.save(any(GuestEntity.class))).thenReturn(new GuestEntity("ABC123", "976543210", "CapGemini", "Vishesh Sharma", "vishesh@gmail.com", "Male", "Agra"));
		
		//checking 
		ResponseEntity<GuestDto> response = guestServiceImpl.edit(1L, updatedDto);
		
		GuestDto resDto = response.getBody();
		
		assertAll(()->assertTrue(updatedDto.getAddress().equals(resDto.getAddress())),
				  ()->assertTrue(updatedDto.getCompany().equals(resDto.getCompany())), 
				  ()->assertTrue(updatedDto.getEmail().equals(resDto.getEmail())),
				  ()->assertTrue(updatedDto.getGender().equals(resDto.getGender())),
				  ()->assertTrue(updatedDto.getMemberCode().equals(resDto.getMemberCode())), 
				  ()->assertTrue(updatedDto.getPhoneNumber().equals(resDto.getPhoneNumber())), 
				  ()->assertTrue(updatedDto.getName().equals(resDto.getName())));
		
		System.out.println("Name of the response guest is : "+resDto.getName());
	}
	
	@Test
	@DisplayName("deleteTest")
	void deleteTest() throws Exception{
		
		//creating a newDto
		GuestDto newDto = new GuestDto(1L, "ABC123", "976543210", "CapGemini", "Kushagra Sharma", "kush@gmail.com", "Male", "Agra");

		guestServiceImpl.create(newDto);  
				
		
		//checking
		ResponseEntity<String> response = guestServiceImpl.delete(1L);
		
		String response1 = response.getBody();
		
		assertTrue(response1.equals("guest deleted"));
		
	}
	

	
	
}
