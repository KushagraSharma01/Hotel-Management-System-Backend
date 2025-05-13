package com.example.ReservationMicroservice.ControllerTest;

import java.util.ArrayList;

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

import com.example.ReservationMicroservice.controller.ReservationController;
import com.example.ReservationMicroservice.dto.ReservationDto;
import com.example.ReservationMicroservice.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;


import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(ReservationController.class)
@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest {

	@Autowired
	private MockMvc mvc; 
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockitoBean
	private ReservationService reservationService;
	
	private ReservationDto reqDto;
	private String request;
	
	@BeforeEach
	public void setup() throws Exception{
		
		reqDto = new ReservationDto(1L, 2L, "ABC123", 12L, 20L, "09/12/2025", "11/12/2025", "AC", new ArrayList<>());
		request = objectMapper.writeValueAsString(reqDto);
		
	}
	
	@Test
	@DisplayName("Create Route Test")
	public void createRouteTest() throws Exception{
		
		Mockito.when(reservationService.create(any(ReservationDto.class))).thenReturn(new ResponseEntity<>(reqDto, HttpStatus.OK));
		
		mvc.perform(post("/reservations/create/1")
					.content(request)
					.contentType(MediaType.APPLICATION_JSON)
					)
		   .andExpect(status().isOk())
		   .andExpect(jsonPath("$.id").value(reqDto.getId()))
		   .andExpect(jsonPath("$.guestId").value(reqDto.getGuestId()))
		   .andExpect(jsonPath("$.numberOfChildren").value(reqDto.getNumberOfChildren()))
		   .andExpect(jsonPath("$.numberofAdults").value(reqDto.getNumberofAdults()))
		   .andExpect(jsonPath("$.check_inDate").value(reqDto.getCheck_inDate()))
		   .andExpect(jsonPath("$.check_outDate").value(reqDto.getCheck_outDate()))
		   .andExpect(jsonPath("$.roomType").value(reqDto.getRoomType()));
		
		
	}
	
	@Test
	@DisplayName("Get by Id Test")
	public void getByIdTest() throws Exception{
		
		Mockito.when(reservationService.get(any(Long.class))).thenReturn(new ResponseEntity<>(reqDto, HttpStatus.OK));
		
		mvc.perform(get("/reservations/get/1"))
		   .andExpect(status().isOk())
		   .andExpect(jsonPath("$.id").value(reqDto.getId()))
		   .andExpect(jsonPath("$.guestId").value(reqDto.getGuestId()))
		   .andExpect(jsonPath("$.numberOfChildren").value(reqDto.getNumberOfChildren()))
		   .andExpect(jsonPath("$.numberofAdults").value(reqDto.getNumberofAdults()))
		   .andExpect(jsonPath("$.check_inDate").value(reqDto.getCheck_inDate()))
		   .andExpect(jsonPath("$.check_outDate").value(reqDto.getCheck_outDate()))
		   .andExpect(jsonPath("$.roomType").value(reqDto.getRoomType()));
		
	}
	
	@Test
	@DisplayName("Edit Test")
	public void editTest() throws Exception{
		
		ReservationDto updatedDto = new ReservationDto(3L, 4L, "HGS435", 12L, 20L, "09/12/2025", "11/12/2025", "Non-AC", new ArrayList<>());
		
		String request2 = objectMapper.writeValueAsString(updatedDto);
		
		Mockito.when(reservationService.edit(any(Long.class), any(ReservationDto.class))).thenReturn(new ResponseEntity<>(updatedDto, HttpStatus.OK));
		
		mvc.perform(put("/reservations/edit/1")
					.content(request2)
					.contentType(MediaType.APPLICATION_JSON))
		   .andExpect(status().isOk())
		   .andExpect(jsonPath("$.id").value(updatedDto.getId()))
		   .andExpect(jsonPath("$.guestId").value(updatedDto.getGuestId()))
		   .andExpect(jsonPath("$.numberOfChildren").value(updatedDto.getNumberOfChildren()))
		   .andExpect(jsonPath("$.numberofAdults").value(updatedDto.getNumberofAdults()))
		   .andExpect(jsonPath("$.check_inDate").value(updatedDto.getCheck_inDate()))
		   .andExpect(jsonPath("$.check_outDate").value(updatedDto.getCheck_outDate()))
		   .andExpect(jsonPath("$.roomType").value(updatedDto.getRoomType()));
		
		
	}
	
	@Test
	@DisplayName("Delete Test")
	public void deleteTest() throws Exception{
		
		Mockito.when(reservationService.delete(any(Long.class))).thenReturn(new ResponseEntity<>("Reservation Deleted", HttpStatus.OK));
		
		mvc.perform(delete("/reservations/delete/1"))
		   .andExpect(status().isOk())
		   .andExpect(content().string("Reservation Deleted"));
		
		
	}
	
	
}
