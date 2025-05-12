package com.example.RoomAndInventoryService.ControllerTest;

import static org.mockito.ArgumentMatchers.any;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.RoomAndInventoryService.controller.RoomController;
import com.example.RoomAndInventoryService.dto.RoomDto;
import com.example.RoomAndInventoryService.service.RoomService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(RoomController.class)
public class RoomControllerTest {
	
	@MockitoBean
	private RoomService roomService;
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	private RoomDto dummyDto;
	
	private RoomDto updatedDto;
	
	@BeforeEach	
	void setup() throws Exception {
		
		HashMap<String, String> dates = new HashMap<>();
		dates.put("10/09/2025", "12/09/2025");
		
		dummyDto = new RoomDto(1L, 12L, List.of(1L), dates, "AC");
		
		updatedDto = new RoomDto(2L, 24L, List.of(6L), dates, "Non-AC");
		
		Mockito.lenient().when(roomService.create(any(RoomDto.class))).thenReturn(new ResponseEntity<>(dummyDto, HttpStatus.OK));
		
		Mockito.lenient().when(roomService.getAll()).thenReturn(new ResponseEntity<>(List.of(dummyDto), HttpStatus.OK));
		
		Mockito.lenient().when(roomService.edit(any(Long.class), any(RoomDto.class))).thenReturn(new ResponseEntity<>(updatedDto, HttpStatus.OK));
		
		Mockito.lenient().when(roomService.delete(any(Long.class))).thenReturn(new ResponseEntity<>("Room deleted", HttpStatus.OK));
		
		Mockito.lenient().when(roomService.filter(any(String.class), any(String.class), any(String.class))).thenReturn(new ResponseEntity<>(List.of(dummyDto), HttpStatus.OK));
		
	}
	
	@Test
	@DisplayName("Create Test")
	void createTest() throws Exception{
		
		String req = mapper.writeValueAsString(dummyDto);
		
		mvc.perform(post("/rooms/create")
					.content(req)
					.contentType(MediaType.APPLICATION_JSON))
		   .andExpect(status().isOk())
		   .andExpect(jsonPath("$.id").value(dummyDto.getId()))
		   .andExpect(jsonPath("$.roomNumber").value(dummyDto.getRoomNumber()))
		   .andExpect(jsonPath("$.guestIds").value(1))
		   .andExpect(jsonPath("$.dates").value(dummyDto.getDates()))
		   .andExpect(jsonPath("$.roomType").value(dummyDto.getRoomType()));
		
	}
	
	@Test
	@DisplayName("GetAll") 
	void getAll() throws Exception{
		
		mvc.perform(get("/rooms/getAll"))
		   .andExpect(status().isOk())
		   .andExpect(jsonPath("$[0].id").value(dummyDto.getId()))
		   .andExpect(jsonPath("$[0].roomNumber").value(dummyDto.getRoomNumber()))
		   .andExpect(jsonPath("$[0].guestIds").value(1))
		   .andExpect(jsonPath("$[0].dates").value(dummyDto.getDates()))
		   .andExpect(jsonPath("$[0].roomType").value(dummyDto.getRoomType()));
		
	}
	
	@Test
	@DisplayName("Edit Test")
	void editTest() throws Exception{
		
		String req = mapper.writeValueAsString(updatedDto);
		
		mvc.perform(put("/rooms/edit/1")
				    .content(req)
				    .contentType(MediaType.APPLICATION_JSON))
		   .andExpect(status().isOk())
		   .andExpect(jsonPath("$.id").value(updatedDto.getId()))
		   .andExpect(jsonPath("$.roomNumber").value(updatedDto.getRoomNumber()))
		   .andExpect(jsonPath("$.guestIds").value(6))
		   .andExpect(jsonPath("$.dates").value(updatedDto.getDates()))
		   .andExpect(jsonPath("$.roomType").value(updatedDto.getRoomType()));
		
	}
	
	@Test
	@DisplayName("Delete Test")
	void deleteTest() throws Exception{
		
		mvc.perform(delete("/rooms/delete/1"))
		   .andExpect(status().isOk())
		   .andExpect(content().string("Room deleted"));
		
	}
	
	@Test
	@DisplayName("Filter Test")
	void filterTest() throws Exception{
		
		mvc.perform(get("/rooms/filter?checkInDate=13/09/2025&checkOutDate=15/09/2025&roomType=Non-AC"))
		   .andExpect(status().isOk())
		   .andExpect(jsonPath("$[0].id").value(dummyDto.getId()))
		   .andExpect(jsonPath("$[0].roomNumber").value(dummyDto.getRoomNumber()))
		   .andExpect(jsonPath("$[0].guestIds").value(1))
		   .andExpect(jsonPath("$[0].dates").value(dummyDto.getDates()))
		   .andExpect(jsonPath("$[0].roomType").value(dummyDto.getRoomType()));
	
		
	}

}
