package com.example.ReservationMicroservice.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.util.ArrayList;
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
import org.springframework.http.ResponseEntity;

import com.example.ReservationMicroservice.dto.ReservationDto;
import com.example.ReservationMicroservice.entity.ReservationEntity;
import com.example.ReservationMicroservice.repository.ReservationRepository;
import com.example.ReservationMicroservice.service.ReservationServiceImpl;
import com.example.ReservationMicroservice.service.RoomServiceProxy;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

	@Mock
	ReservationRepository reservationRepository;
	
	@Mock
	ModelMapper mapper;
	
	@Mock
	RoomServiceProxy proxy;
	
	@InjectMocks
	ReservationServiceImpl reservationServiceImpl;
	
	List<ReservationEntity> dummyReservations;
	
	@BeforeEach
	public void setup() {
		
		dummyReservations = new ArrayList<>();
		
		dummyReservations.add(new ReservationEntity( 2L, "ABC123", 10L, 20L, "09/08/2025", "12/08/2025", "AC", List.of(2L,3L,4L)) );
		
		//mocking findAll method
		Mockito.lenient().when(reservationRepository.findAll()).thenReturn(dummyReservations);
		
		Optional<ReservationEntity> response = Optional.of(new ReservationEntity( 2L, "ABC123", 10L, 20L, "09/08/2025", "12/08/2025", "AC", List.of(2L,3L,4L)));
		
		//mocking findById method
		Mockito.lenient().when(reservationRepository.findById(any(Long.class))).thenReturn(response);
		
		//mocking save method
		Mockito.lenient().when(reservationRepository.save(any(ReservationEntity.class))).thenReturn(new ReservationEntity( 2L, "ABC123", 10L, 20L, "09/08/2025", "12/08/2025", "AC", List.of(2L,3L,4L)));
		
		//mocking mapper
		Mockito.lenient().when(mapper.map(any(ReservationDto.class), eq(ReservationEntity.class))).thenReturn(new ReservationEntity( 2L, "ABC123", 10L, 20L, "09/08/2025", "12/08/2025", "AC", List.of(2L,3L,4L)));
		Mockito.lenient().when(mapper.map(any(ReservationEntity.class), eq(ReservationDto.class))).thenReturn(new ReservationDto( 1L, 2L, "ABC123", 10L, 20L, "09/08/2025", "12/08/2025", "AC", List.of(2L,3L,4L)));
		
	}
	
	@Test
	@DisplayName("creation Test")
	void createTest() throws Exception {
		
		ReservationDto newReservation = new ReservationDto( 1L, 2L, "ABC123", 10L, 20L, "09/08/2025", "12/08/2025", "AC", List.of(2L,3L,4L));
		 
		// checking
		ResponseEntity<ReservationDto> response = reservationServiceImpl.create(newReservation);
		
		ReservationDto responseDto = response.getBody();
		
//		System.out.println(responseDto.getCheck_inDate());
		System.out.println(newReservation.getCheck_inDate());
		
		assertAll(()->assertTrue(newReservation.getCheck_inDate().equals(responseDto.getCheck_inDate())),
				  ()->assertTrue(newReservation.getCheck_outDate().equals(responseDto.getCheck_outDate())),
				  ()->assertTrue(newReservation.getCode().equals(responseDto.getCode())),
				  ()->assertTrue(newReservation.getGuestId().equals(responseDto.getGuestId())),
				  ()->assertTrue(newReservation.getNumberofAdults().equals(responseDto.getNumberofAdults())),
		          ()->assertTrue(newReservation.getNumberOfChildren().equals(responseDto.getNumberOfChildren())),
		          ()->assertTrue(newReservation.getRoomNumbers().equals(responseDto.getRoomNumbers())),
		          ()->assertTrue(newReservation.getRoomType().equals(responseDto.getRoomType())));
		
	}
	
	@Test
	@DisplayName("getById Test")
	void getByIdTest() throws Exception {
		
		ReservationDto newReservation = new ReservationDto( 1L, 2L, "ABC123", 10L, 20L, "09/08/2025", "12/08/2025", "AC", List.of(2L,3L,4L));
		
		reservationServiceImpl.create(newReservation);
		
		ResponseEntity<ReservationDto> response = reservationServiceImpl.get(1L);
		
		ReservationDto responseDto = response.getBody();
		
		assertAll(()->assertTrue(newReservation.getCheck_inDate().equals(responseDto.getCheck_inDate())),
				  ()->assertTrue(newReservation.getCheck_outDate().equals(responseDto.getCheck_outDate())),
				  ()->assertTrue(newReservation.getCode().equals(responseDto.getCode())),
				  ()->assertTrue(newReservation.getGuestId().equals(responseDto.getGuestId())),
				  ()->assertTrue(newReservation.getNumberofAdults().equals(responseDto.getNumberofAdults())),
		          ()->assertTrue(newReservation.getNumberOfChildren().equals(responseDto.getNumberOfChildren())),
		          ()->assertTrue(newReservation.getRoomNumbers().equals(responseDto.getRoomNumbers())),
		          ()->assertTrue(newReservation.getRoomType().equals(responseDto.getRoomType())));
		
		
	}
	
	@Test
	@DisplayName("getAllForGuest Test")
	void getAllForGuestTest() throws Exception {
		
		ReservationDto newReservation = new ReservationDto( 1L, 2L, "ABC123", 10L, 20L, "09/08/2025", "12/08/2025", "AC", List.of(2L,3L,4L));
		
		reservationServiceImpl.create(newReservation);
		
		ResponseEntity<List<ReservationDto>> response = reservationServiceImpl.getAllForGuest(2L);
		
		List<ReservationDto> responseList = response.getBody();
		
		assertAll(()->assertTrue(responseList.get(0).getCheck_inDate().equals(newReservation.getCheck_inDate())),
				  ()->assertTrue(responseList.get(0).getCheck_outDate().equals(newReservation.getCheck_outDate())),
				  ()->assertTrue(responseList.get(0).getCode().equals(newReservation.getCode())),
				  ()->assertTrue(responseList.get(0).getGuestId().equals(newReservation.getGuestId())),
				  ()->assertTrue(responseList.get(0).getNumberofAdults().equals(newReservation.getNumberofAdults())),
				  ()->assertTrue(responseList.get(0).getNumberOfChildren().equals(newReservation.getNumberOfChildren())),
				  ()->assertTrue(responseList.get(0).getRoomNumbers().equals(newReservation.getRoomNumbers())),
				  ()->assertTrue(responseList.get(0).getRoomType().equals(newReservation.getRoomType())));
		
	}
	
	@Test
	@DisplayName("getAll Test")
	void getAllTest() throws Exception {
		
		
		ResponseEntity<List<ReservationDto>> response = reservationServiceImpl.getAll();
		
		List<ReservationDto> responseList = response.getBody();
		
		ReservationDto present1 = new ReservationDto(1L, 2L, "ABC123", 10L, 20L, "09/08/2025", "12/08/2025", "AC", List.of(2L,3L,4L)) ;
//		
		ReservationDto present2 = new ReservationDto(2L, 4L, "ABC456", 2L, 13L, "09/10/2025", "12/10/2025", "Non-AC", List.of(5L,6L,7L));
		
		System.out.println(responseList.size());
		
		
		assertAll(()-> assertTrue(responseList.get(0).getCheck_inDate().equals(present1.getCheck_inDate())),
				  ()-> assertTrue(responseList.get(0).getCheck_outDate().equals(present1.getCheck_outDate())),
				  ()-> assertTrue(responseList.get(0).getGuestId().equals(present1.getGuestId())),
				  ()-> assertTrue(responseList.get(0).getNumberofAdults().equals(present1.getNumberofAdults())),
				  ()-> assertTrue(responseList.get(0).getNumberOfChildren().equals(present1.getNumberOfChildren())),
				  ()-> assertTrue(responseList.get(0).getRoomNumbers().equals(present1.getRoomNumbers())),
				  ()-> assertTrue(responseList.get(0).getRoomType().equals(present1.getRoomType())));
		
	}
	
	@Test
	@DisplayName("delete Test")
	void deleteTest() throws Exception {
		
		ResponseEntity<String> response = reservationServiceImpl.delete(1L);
		
		String response1 = response.getBody();
		
		assertTrue(response1.equals("Reservation deleted"));
		
	}
	
	
}
