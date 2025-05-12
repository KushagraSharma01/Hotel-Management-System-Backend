package com.example.RoomAndInventoryService.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.util.HashMap;
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

import com.example.RoomAndInventoryService.dto.RoomDto;
import com.example.RoomAndInventoryService.entity.RoomEntity;
import com.example.RoomAndInventoryService.repository.RoomRepository;
import com.example.RoomAndInventoryService.service.RoomServiceImpl;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

	@Mock
	private RoomRepository roomRepository;
	
	@Mock 
	private ModelMapper mapper;
	
	@InjectMocks
	private RoomServiceImpl roomService;
	
	private RoomDto dummyDto;
	
	private RoomDto updatedDto;
	
	@BeforeEach
	void setup() {
		
		HashMap<String, String> dates = new HashMap<>();
		dates.put("10/09/2025", "12/09/2025");
		
		dummyDto = new RoomDto(1L, 12L, List.of(1L), dates, "AC");
		
		updatedDto = new RoomDto(2L, 24L, List.of(6L), dates, "Non-AC");
		
		Mockito.lenient().when(roomRepository.findAll()).thenReturn(List.of(new RoomEntity(1L, 12L, List.of(1L), dates, "AC")));
		
		Mockito.lenient().when(roomRepository.save(any(RoomEntity.class))).thenReturn(new RoomEntity(1L, 12L, List.of(1L), dates, "AC"));
		
		Mockito.lenient().when(roomRepository.findById(any(Long.class))).thenReturn(Optional.of(new RoomEntity(1L, 12L, List.of(1L), dates, "AC")));
		
		
		Mockito.lenient().when(mapper.map(any(RoomDto.class), eq(RoomEntity.class))).thenReturn(new RoomEntity(1L, 12L, List.of(1L), dates, "AC"));
		Mockito.lenient().when(mapper.map(any(RoomEntity.class), eq(RoomDto.class))).thenReturn(dummyDto);
		
		
	}
	
	@Test
	@DisplayName("Create Test")
	void createTest() throws Exception{
		
		RoomDto resDto = roomService.create(dummyDto).getBody();
		
		
		assertAll(()->assertTrue(dummyDto.getId().equals(resDto.getId())),
				  ()->assertTrue(dummyDto.getDates().equals(resDto.getDates())),
				  ()->assertTrue(dummyDto.getGuestIds().get(0).equals(resDto.getGuestIds().get(0))),
				  ()->assertTrue(dummyDto.getRoomNumber().equals(resDto.getRoomNumber())),
				  ()->assertTrue(dummyDto.getRoomType().equals(resDto.getRoomType())));
		
	}
	
	@Test
	@DisplayName("getAll Test")
	void getAllTest() throws Exception{
		
		RoomDto resDto = roomService.getAll().getBody().get(0);
		
		assertAll(()->assertTrue(dummyDto.getId().equals(resDto.getId())),
				  ()->assertTrue(dummyDto.getDates().equals(resDto.getDates())),
				  ()->assertTrue(dummyDto.getGuestIds().get(0).equals(resDto.getGuestIds().get(0))),
				  ()->assertTrue(dummyDto.getRoomNumber().equals(resDto.getRoomNumber())),
				  ()->assertTrue(dummyDto.getRoomType().equals(resDto.getRoomType())));
		
	}
	
	@Test
	@DisplayName("Edit Test")
	void editTest() throws Exception{
		
		Mockito.lenient().when(roomRepository.findById(any(Long.class))).thenReturn(Optional.of(new RoomEntity(2L, 24L, List.of(6L), new HashMap<>(), "Non-AC")));
		
		Mockito.lenient().when(roomRepository.save(any(RoomEntity.class))).thenReturn(new RoomEntity(2L, 24L, List.of(6L), new HashMap<>(), "Non-AC"));
		
		Mockito.lenient().when(mapper.map(any(RoomEntity.class), eq(RoomDto.class))).thenReturn(updatedDto);
		
		RoomDto resDto = roomService.edit(1L, updatedDto).getBody();
		
		assertAll(()->assertTrue(updatedDto.getId().equals(resDto.getId())),
				  ()->assertTrue(updatedDto.getDates().equals(resDto.getDates())),
				  ()->assertTrue(updatedDto.getGuestIds().get(0).equals(resDto.getGuestIds().get(0))),
				  ()->assertTrue(updatedDto.getRoomNumber().equals(resDto.getRoomNumber())),
				  ()->assertTrue(updatedDto.getRoomType().equals(resDto.getRoomType())));
			
	}
	
	@Test
	@DisplayName("Delete Test")
	void deleteTest() throws Exception{
		
		String response = roomService.delete(1L).getBody();
		
		assertTrue(response.equals("Room deleted"));
		
	}
	
	@Test
	@DisplayName("Filter Test")
	void filterTest() throws Exception{
		
		RoomDto resDto = roomService.filter("13/09/2025", "15/09/2025", "AC").getBody().get(0);
		
		assertAll(()->assertTrue(dummyDto.getId().equals(resDto.getId())),
				  ()->assertTrue(dummyDto.getDates().equals(resDto.getDates())),
				  ()->assertTrue(dummyDto.getGuestIds().get(0).equals(resDto.getGuestIds().get(0))),
				  ()->assertTrue(dummyDto.getRoomNumber().equals(resDto.getRoomNumber())),
				  ()->assertTrue(dummyDto.getRoomType().equals(resDto.getRoomType())));
		
	}
	
}
