package com.example.ReservationMicroservice.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.ReservationMicroservice.dto.ProductRequest;
import com.example.ReservationMicroservice.dto.ReservationDto;
import com.example.ReservationMicroservice.dto.RoomDto;
import com.example.ReservationMicroservice.dto.StripeResponse;
import com.example.ReservationMicroservice.entity.ReservationEntity;
import com.example.ReservationMicroservice.repository.ReservationRepository;
import com.example.ReservationMicroservice.service.PaymentServiceProxy;
import com.example.ReservationMicroservice.service.ReservationServiceImpl;
import com.example.ReservationMicroservice.service.RoomServiceProxy;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

	@Mock
	private ReservationRepository reservationRepository;
	
	@Mock
	private ModelMapper mapper;
	
	@Mock
	private RoomServiceProxy roomProxy;
	
	@Mock
	private PaymentServiceProxy paymentProxy;
	
	@InjectMocks
	private ReservationServiceImpl reservationServiceImpl;
	
	private ReservationDto newReservation;
	
	private List<ReservationEntity> dummyReservations;
	
	@BeforeEach
	public void setup() throws Exception {
		
		newReservation = new ReservationDto( 1L, 2L, "ABC123", 10L, 20L, "09/08/2025", "12/08/2025", "AC", List.of(7L), "sessionId", "Success", new Date());
		
		dummyReservations = new ArrayList<>();
		
		dummyReservations.add(new ReservationEntity(1L, 2L, "ABC123", 10L, 20L, "09/08/2025", "12/08/2025", "AC", List.of(7L), "sessionId", "Success", new Date()));
		
		//mocking findAll method
		Mockito.lenient().when(reservationRepository.findAll()).thenReturn(dummyReservations);
		
		Optional<ReservationEntity> response = Optional.of(new ReservationEntity( 2L, "ABC123", 10L, 20L, "09/08/2025", "12/08/2025", "AC", List.of(7L)));
		
		//mocking findById method
		Mockito.lenient().when(reservationRepository.findById(any(Long.class))).thenReturn(response);
		
		//mocking save method
		Mockito.lenient().when(reservationRepository.save(any(ReservationEntity.class))).thenReturn(new ReservationEntity( 2L, "ABC123", 10L, 20L, "09/08/2025", "12/08/2025", "AC", List.of(7L)));
		
		//mocking mapper
		Mockito.lenient().when(mapper.map(any(ReservationDto.class), eq(ReservationEntity.class))).thenReturn(new ReservationEntity( 1L, 2L, "ABC123", 10L, 20L, "09/08/2025", "12/08/2025", "AC", List.of(7L), "sessionId", "Success", new Date()));
		Mockito.lenient().when(mapper.map(any(ReservationEntity.class), eq(ReservationDto.class))).thenReturn(new ReservationDto( 1L, 2L, "ABC123", 10L, 20L, "09/08/2025", "12/08/2025", "AC", List.of(7L), "sessionId", "Success", new Date()));
		
		//mocking the room service response
		Mockito.lenient().when(roomProxy.filter(any(String.class))).thenReturn(new ResponseEntity<>(List.of(new RoomDto(1L, 7L, "AC", 24L)), HttpStatus.OK));
		
		//mocking the payment service response
		Mockito.lenient().when(paymentProxy.checkout(any(ProductRequest.class))).thenReturn(new ResponseEntity<>(new StripeResponse("Success", "Payment is Pending", "SessionId", "SessionUrl"), HttpStatus.OK));
	}
	
	@Test
	@DisplayName("creation Test")
	void createTest() throws Exception {
				 
		// checking
		ResponseEntity<StripeResponse> response = reservationServiceImpl.create(newReservation);
		
		StripeResponse responseDto = response.getBody();
		
		System.out.println(responseDto.getMessage()+" "+responseDto.getSessionId()+" "+responseDto.getSessionUrl());
		
//		System.out.println(responseDto.getCheck_inDate());
		System.out.println(newReservation.getCheck_inDate());
		
		assertAll(()->assertTrue(responseDto.getStatus().equals("Success")),
				  ()->assertTrue(responseDto.getMessage().equals("Payment is Pending")),
				  ()->assertTrue(responseDto.getSessionId().equals("SessionId")),
				  ()->assertTrue(responseDto.getSessionUrl().equals("SessionUrl")));
		
	}
	
	@Test
	@DisplayName("getById Test")
	void getByIdTest() throws Exception {
				
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
		
		ReservationDto present = new ReservationDto(1L, 2L, "ABC123", 10L, 20L, "09/08/2025", "12/08/2025", "AC", List.of(7L), "sessionId", "Success", new Date()) ;
//				
		System.out.println(responseList.size());
		
		
		assertAll(()-> assertTrue(responseList.get(0).getCheck_inDate().equals(present.getCheck_inDate())),
				  ()-> assertTrue(responseList.get(0).getCheck_outDate().equals(present.getCheck_outDate())),
				  ()-> assertTrue(responseList.get(0).getGuestId().equals(present.getGuestId())),
				  ()-> assertTrue(responseList.get(0).getNumberofAdults().equals(present.getNumberofAdults())),
				  ()-> assertTrue(responseList.get(0).getNumberOfChildren().equals(present.getNumberOfChildren())),
				  ()-> assertTrue(responseList.get(0).getRoomNumbers().equals(present.getRoomNumbers())),
				  ()-> assertTrue(responseList.get(0).getRoomType().equals(present.getRoomType())));
		
	}
	
	@Test
	@DisplayName("delete Test")
	void deleteTest() throws Exception {
		
		ResponseEntity<String> response = reservationServiceImpl.delete(1L);
		
		String response1 = response.getBody();
		
		assertTrue(response1.equals("Reservation deleted"));
		
	}
	
	
}
