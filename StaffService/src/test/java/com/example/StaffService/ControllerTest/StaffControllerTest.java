package com.example.StaffService.ControllerTest;

import static org.mockito.ArgumentMatchers.any;

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

import com.example.StaffService.controller.StaffController;
import com.example.StaffService.dto.StaffDto;
import com.example.StaffService.service.StaffService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;;



@WebMvcTest(StaffController.class)
public class StaffControllerTest {

	@MockitoBean 
	private StaffService staffService;
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	private StaffDto dummyDto;
	
	private StaffDto updatedDto;
	
	@BeforeEach
	void setup() throws Exception{
		
		dummyDto = new StaffDto(1L, "ABC123", "Priyanshu Kumar", "Bihar", "ABC123", 60000L, 22, "Room Service", "priyanshu@gmail.com");
		
		updatedDto = new StaffDto(1L, "ABC123", "Vishesh Sharma", "Agra", "ABC123", 60000L, 22, "Room Service", "vishesh@gmail.com");
		
		Mockito.when(staffService.create(any(StaffDto.class))).thenReturn(new ResponseEntity<>(dummyDto, HttpStatus.OK));
		
		Mockito.when(staffService.get(any(Long.class))).thenReturn(new ResponseEntity<>(dummyDto, HttpStatus.OK));
		
		Mockito.when(staffService.getAll()).thenReturn(new ResponseEntity<>(List.of(dummyDto), HttpStatus.OK));
		
		Mockito.when(staffService.edit(any(Long.class), any(StaffDto.class))).thenReturn(new ResponseEntity<>(updatedDto, HttpStatus.OK));
	
		Mockito.when(staffService.delete(any(Long.class))).thenReturn(new ResponseEntity<>("Staff Deleted Successfully", HttpStatus.OK));
	}
	
	
	@Test
	@DisplayName("Create Test")
	void createTest() throws Exception {
		
		String req = mapper.writeValueAsString(dummyDto);
		
		mvc.perform(post("/staff/create")
					.content(req)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(dummyDto.getId()))
			.andExpect(jsonPath("$.name").value(dummyDto.getName()))
			.andExpect(jsonPath("$.address").value(dummyDto.getAddress()))
			.andExpect(jsonPath("$.nic").value(dummyDto.getNic()))
			.andExpect(jsonPath("$.salary").value(dummyDto.getSalary()))
			.andExpect(jsonPath("$.age").value(dummyDto.getAge()))
			.andExpect(jsonPath("$.email").value(dummyDto.getEmail()))
			.andExpect(jsonPath("$.occupation").value(dummyDto.getOccupation()));
			
	}
	
	@Test
	@DisplayName("Get Test")
	void getTest() throws Exception {
		
		
		mvc.perform(get("/staff/get/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(dummyDto.getId()))
		.andExpect(jsonPath("$.name").value(dummyDto.getName()))
		.andExpect(jsonPath("$.address").value(dummyDto.getAddress()))
		.andExpect(jsonPath("$.nic").value(dummyDto.getNic()))
		.andExpect(jsonPath("$.salary").value(dummyDto.getSalary()))
		.andExpect(jsonPath("$.age").value(dummyDto.getAge()))
		.andExpect(jsonPath("$.email").value(dummyDto.getEmail()))
		.andExpect(jsonPath("$.occupation").value(dummyDto.getOccupation()));
		
	}
	
	@Test
	@DisplayName("GetAll Test")
	void getAllTest() throws Exception{
		
		mvc.perform(get("/staff/getAll"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].id").value(dummyDto.getId()))
		.andExpect(jsonPath("$[0].name").value(dummyDto.getName()))
		.andExpect(jsonPath("$[0].address").value(dummyDto.getAddress()))
		.andExpect(jsonPath("$[0].nic").value(dummyDto.getNic()))
		.andExpect(jsonPath("$[0].salary").value(dummyDto.getSalary()))
		.andExpect(jsonPath("$[0].age").value(dummyDto.getAge()))
		.andExpect(jsonPath("$[0].email").value(dummyDto.getEmail()))
		.andExpect(jsonPath("$[0].occupation").value(dummyDto.getOccupation()));
		
	}
	
	@Test
	@DisplayName("Edit Test")
	void editTest() throws Exception{
		
		String req = mapper.writeValueAsString(updatedDto);
		
		mvc.perform(put("/staff/edit/1")
				.content(req)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(updatedDto.getId()))
		.andExpect(jsonPath("$.name").value(updatedDto.getName()))
		.andExpect(jsonPath("$.address").value(updatedDto.getAddress()))
		.andExpect(jsonPath("$.nic").value(updatedDto.getNic()))
		.andExpect(jsonPath("$.salary").value(updatedDto.getSalary()))
		.andExpect(jsonPath("$.age").value(updatedDto.getAge()))
		.andExpect(jsonPath("$.email").value(updatedDto.getEmail()))
		.andExpect(jsonPath("$.occupation").value(updatedDto.getOccupation()));
		
		
	}
	
	@Test
	@DisplayName("Delete Test")
	void deleteTest() throws Exception{
		
		mvc.perform(delete("/staff/delete/1"))
			.andExpect(status().isOk())
		   .andExpect(content().string("Staff Deleted Successfully"));
		
	}
}
