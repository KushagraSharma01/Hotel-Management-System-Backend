package com.example.ReservationMicroservice.ControllerTest;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.ReservationMicroservice.controller.GuestController;
import com.example.ReservationMicroservice.dto.GuestDto;
import com.example.ReservationMicroservice.service.GuestService;
import com.fasterxml.jackson.databind.ObjectMapper;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(GuestController.class)
@ExtendWith(MockitoExtension.class)
public class GuestControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockitoBean
	private GuestService guestService;
	
	@Autowired
	private ObjectMapper mapper;
	
	private GuestDto requestDto;
	private String request;
	
	@BeforeEach
	public void setup() throws Exception{
		
		requestDto = new GuestDto(1L, "ABC123", "9876543210", "CapGemini", "Kushagra Sharma", "kush@gmai.com", "Male", "Agra");
		
		request = mapper.writeValueAsString(requestDto);
	}
	
	@Test
	@DisplayName("Create Guest Test")
	public void createTest() throws Exception{
		
		Mockito.when(guestService.create(any(GuestDto.class))).thenReturn(new ResponseEntity<>(requestDto, HttpStatus.OK));
		
		mvc.perform(post("/guest/create")
					.content(request)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(requestDto.getId()))
			.andExpect(jsonPath("$.memberCode").value(requestDto.getMemberCode()))
			.andExpect(jsonPath("$.phoneNumber").value(requestDto.getPhoneNumber()))
			.andExpect(jsonPath("$.company").value(requestDto.getCompany()))
			.andExpect(jsonPath("$.name").value(requestDto.getName()))
			.andExpect(jsonPath("$.gender").value(requestDto.getGender()))
			.andExpect(jsonPath("$.email").value(requestDto.getEmail()))
			.andExpect(jsonPath("$.address").value(requestDto.getAddress()));
			
		
	}
	
	@Test
	@DisplayName("Get Guest Test")
	public void getTest() throws Exception{
		
		Mockito.when(guestService.get(any(Long.class))).thenReturn(new ResponseEntity<>(requestDto, HttpStatus.OK));
		
		mvc.perform(get("/guest/get/1"))
		   .andExpect(status().isOk())
		   .andExpect(jsonPath("$.id").value(requestDto.getId()))
		   .andExpect(jsonPath("$.memberCode").value(requestDto.getMemberCode()))
		   .andExpect(jsonPath("$.phoneNumber").value(requestDto.getPhoneNumber()))
		   .andExpect(jsonPath("$.company").value(requestDto.getCompany()))
		   .andExpect(jsonPath("$.name").value(requestDto.getName()))
		   .andExpect(jsonPath("$.gender").value(requestDto.getGender()))
		   .andExpect(jsonPath("$.email").value(requestDto.getEmail()))
		   .andExpect(jsonPath("$.address").value(requestDto.getAddress()));  
		
	}
	
	@Test
	@DisplayName("Edit Test")
	public void editTest() throws Exception{
		
		GuestDto updatedDto = new GuestDto(1L, "HSG762", "9876543232", "CapGemini", "Vishesh Sharma", "kush@gmai.com", "Male", "Agra");
		
		Mockito.when(guestService.edit(any(Long.class), any(GuestDto.class))).thenReturn(new ResponseEntity<>(updatedDto, HttpStatus.OK));
		
		String request1 = mapper.writeValueAsString(updatedDto);
		
		mvc.perform(put("/guest/edit/1")
					.content(request1)
					.contentType(MediaType.APPLICATION_JSON))
		   .andExpect(status().isOk())
		   .andExpect(jsonPath("$.id").value(updatedDto.getId()))
		   .andExpect(jsonPath("$.memberCode").value(updatedDto.getMemberCode()))
		   .andExpect(jsonPath("$.phoneNumber").value(updatedDto.getPhoneNumber()))
		   .andExpect(jsonPath("$.company").value(updatedDto.getCompany()))
		   .andExpect(jsonPath("$.name").value(updatedDto.getName()))
		   .andExpect(jsonPath("$.gender").value(updatedDto.getGender()))
		   .andExpect(jsonPath("$.email").value(updatedDto.getEmail()))
		   .andExpect(jsonPath("$.address").value(updatedDto.getAddress()));  
		
	}
	
	@Test
	@DisplayName("Delete Test")
	public void deleteTest() throws Exception{
		
		Mockito.when(guestService.delete(any(Long.class))).thenReturn(new ResponseEntity<>("Guest Deleted", HttpStatus.OK));
		
		mvc.perform(delete("/guest/delete/1"))
			.andExpect(status().isOk())
			.andExpect(content().string("Guest Deleted"));

		
	}

}
