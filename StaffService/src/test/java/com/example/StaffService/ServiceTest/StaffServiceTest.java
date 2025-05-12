package com.example.StaffService.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.example.StaffService.dto.StaffDto;
import com.example.StaffService.entity.StaffEntity;
import com.example.StaffService.repository.StaffRepository;
import com.example.StaffService.service.StaffServiceImpl;

@ExtendWith(MockitoExtension.class)
public class StaffServiceTest {
	
	
	@Mock
	private StaffRepository staffRepository;
	
	@Mock
	private ModelMapper mapper;
	
	@InjectMocks
	private StaffServiceImpl staffService;
	
	private StaffDto dummyDto;
	
	
	@BeforeEach
	void setup() {
		
		dummyDto = new StaffDto(1L, "ABC123", "Priyanshu Kumar", "Bihar", "ABC123", 60000L, 22, "Room Service", "priyanshu@gmail.com");
		
		Mockito.lenient().when(staffRepository.save(any(StaffEntity.class))).thenReturn(new StaffEntity(1L, "ABC123", "Priyanshu Kumar", "Bihar", "ABC123", 60000L, 22, "Room Service", "priyanshu@gmail.com"));
		
		Mockito.lenient().when(staffRepository.findById(any(Long.class))).thenReturn(Optional.of(new StaffEntity(1L, "ABC123", "Priyanshu Kumar", "Bihar", "ABC123", 60000L, 22, "Room Service", "priyanshu@gmail.com")));

		Mockito.lenient().when(staffRepository.findAll()).thenReturn(List.of(new StaffEntity(1L, "ABC123", "Priyanshu Kumar", "Bihar", "ABC123", 60000L, 22, "Room Service", "priyanshu@gmail.com")));
		
		Mockito.lenient().when(mapper.map(any(StaffDto.class), eq(StaffEntity.class))).thenReturn(new StaffEntity(1L, "ABC123", "Priyanshu Kumar", "Bihar", "ABC123", 60000L, 22, "Room Service", "priyanshu@gmail.com"));
		
		Mockito.lenient().when(mapper.map(any(StaffEntity.class), eq(StaffDto.class))).thenReturn(dummyDto);
	}
	
	@Test
	@DisplayName("Create Test")
	void createTest() throws Exception {
		
		StaffDto resDto = staffService.create(dummyDto).getBody();
		
		assertAll(()->assertTrue(dummyDto.getAddress().equals(resDto.getAddress())),
				()->assertTrue(dummyDto.getAge() == resDto.getAge()),
				()->assertTrue(dummyDto.getCode().equals(resDto.getCode())),
				()->assertTrue(dummyDto.getEmail().equals(resDto.getEmail())),
				()->assertTrue(dummyDto.getId().equals(resDto.getId())),
				()->assertTrue(dummyDto.getName().equals(resDto.getName())),
				()->assertTrue(dummyDto.getNic().equals(resDto.getNic())),
				()->assertTrue(dummyDto.getOccupation().equals(resDto.getOccupation())),
				()->assertTrue(dummyDto.getSalary().equals(resDto.getSalary())));
	}
	
	@Test
	@DisplayName("Get Test")
	void getTest() throws Exception{
		
		StaffDto resDto = staffService.get(1L).getBody();
		
		assertAll(()->assertTrue(dummyDto.getAddress().equals(resDto.getAddress())),
				()->assertTrue(dummyDto.getAge() == resDto.getAge()),
				()->assertTrue(dummyDto.getCode().equals(resDto.getCode())),
				()->assertTrue(dummyDto.getEmail().equals(resDto.getEmail())),
				()->assertTrue(dummyDto.getId().equals(resDto.getId())),
				()->assertTrue(dummyDto.getName().equals(resDto.getName())),
				()->assertTrue(dummyDto.getNic().equals(resDto.getNic())),
				()->assertTrue(dummyDto.getOccupation().equals(resDto.getOccupation())),
				()->assertTrue(dummyDto.getSalary().equals(resDto.getSalary())));
		
	}
	
	@Test
	@DisplayName("GetAll Test")
	void getAllTest() throws Exception {
			
		List<StaffDto> resList = staffService.getAll().getBody();
		
		assertAll(()->assertTrue(dummyDto.getAddress().equals(resList.get(0).getAddress())),
				()->assertTrue(dummyDto.getAge() == resList.get(0).getAge()),
				()->assertTrue(dummyDto.getCode().equals(resList.get(0).getCode())),
				()->assertTrue(dummyDto.getEmail().equals(resList.get(0).getEmail())),
				()->assertTrue(dummyDto.getId().equals(resList.get(0).getId())),
				()->assertTrue(dummyDto.getName().equals(resList.get(0).getName())),
				()->assertTrue(dummyDto.getNic().equals(resList.get(0).getNic())),
				()->assertTrue(dummyDto.getOccupation().equals(resList.get(0).getOccupation())),
				()->assertTrue(dummyDto.getSalary().equals(resList.get(0).getSalary())));
		
	}
	
	@Test
	@DisplayName("Edit Test")
	void edit() throws Exception{
		
		StaffDto updatedDto = new StaffDto(1L, "ABC123", "Vishesh Sharma", "Agra", "ABC123", 60000L, 22, "Room Service", "vishesh@gmail.com");
		
		Mockito.lenient().when(staffRepository.save(any(StaffEntity.class))).thenReturn(new StaffEntity(1L, "ABC123", "Vishesh Sharma", "Agra", "ABC123", 60000L, 22, "Room Service", "vishesh@gmail.com"));
		
		Mockito.lenient().when(mapper.map(any(StaffEntity.class), eq(StaffDto.class))).thenReturn(updatedDto);
		
		StaffDto resDto = staffService.edit(1L, updatedDto).getBody();
		
		assertAll(()->assertTrue(updatedDto.getAddress().equals(resDto.getAddress())),
				()->assertTrue(updatedDto.getAge() == resDto.getAge()),
				()->assertTrue(updatedDto.getCode().equals(resDto.getCode())),
				()->assertTrue(updatedDto.getEmail().equals(resDto.getEmail())),
				()->assertTrue(updatedDto.getId().equals(resDto.getId())),
				()->assertTrue(updatedDto.getName().equals(resDto.getName())),
				()->assertTrue(updatedDto.getNic().equals(resDto.getNic())),
				()->assertTrue(updatedDto.getOccupation().equals(resDto.getOccupation())),
				()->assertTrue(updatedDto.getSalary().equals(resDto.getSalary())));
		
	}
	
	@Test
	@DisplayName("Delete Test")
	void deleteTest() throws Exception{
		
		String response = staffService.delete(1L).getBody();
		
		assertTrue(response.equals("Staff Deleted Successfully"));
		
	}
	

}
