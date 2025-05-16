package com.example.ReservationMicroservice.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.ReservationMicroservice.dto.ProductRequest;
import com.example.ReservationMicroservice.dto.ReservationDto;
import com.example.ReservationMicroservice.dto.RoomDto;
import com.example.ReservationMicroservice.dto.StripeResponse;
import com.example.ReservationMicroservice.entity.ReservationEntity;
import com.example.ReservationMicroservice.exceptions.ReservationNotFoundException;
import com.example.ReservationMicroservice.exceptions.ServiceDownException;
import com.example.ReservationMicroservice.exceptions.RoomsNotAvailableException;
import com.example.ReservationMicroservice.repository.ReservationRepository;

import feign.FeignException;

@Service
public class ReservationServiceImpl implements ReservationService{

	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired 
	private ModelMapper mapper;
	 
	@Autowired
	private RoomServiceProxy proxy;
	
	@Autowired 
	private PaymentServiceProxy paymentProxy;
	
	public ResponseEntity<StripeResponse> create(ReservationDto inDto) throws Exception{
		
		ReservationEntity newReservation = mapper.map(inDto, ReservationEntity.class);
		
		// getting all rooms from room service of type roomType
		Set<RoomDto> roomDtos = null;
		
		try {
			roomDtos = proxy.filter(inDto.getRoomType()).getBody().stream().collect(Collectors.toSet());
		}
		catch(FeignException e) {
			
			throw new ServiceDownException("Room service is down currently, Please try again later");
			
		}
		Set<Long> rooms = roomDtos.stream().map(room -> room.getRoomNumber()).collect(Collectors.toSet());
		
		//getting all notAvailable rooms 
		Set<Long> notAvailable = notAvailable(inDto.getCheck_inDate(), inDto.getCheck_outDate(), inDto.getRoomType());
		
		// removing the notAvailable rooms from all rooms of given type
		for(Long i: notAvailable) {
			if(rooms.contains(i))
				rooms.remove(i);
		}
		
		boolean available = true; 
		
		for(Long i: inDto.getRoomNumbers()) {
			if(!rooms.contains(i)) {
				available = false;
				break;
			}
		}
		
		if(!available || rooms.isEmpty())
			throw new RoomsNotAvailableException("Rooms not available for reservation");
		
		//calculating total amount
		Long amount = 0L;
		for(RoomDto room: roomDtos) {
			if(rooms.contains(room.getRoomNumber()))
				amount += room.getPrice();
		}
		
		//creating a ProductRequest to send to payment service
		ProductRequest newProduct = new ProductRequest(amount*100, 1L, "Make Reservation", "INR");
		
		//checking out to payment service
		StripeResponse response = null;
		
		try {
			response = paymentProxy.checkout(newProduct).getBody();
		}
		catch(FeignException e) {
			throw new ServiceDownException("Payment service is down currently, Please try again later");	
		}
		
		newReservation.setStatus("Pending");
		newReservation.setDate(new Date());
		newReservation.setSessionId(response.getSessionId());
		
		newReservation = reservationRepository.save(newReservation);
		
		ReservationDto resDto =  mapper.map(newReservation, ReservationDto.class);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
	public ResponseEntity<ReservationDto> get(Long id) throws Exception{
		
		ReservationDto resDto = mapper.map(reservationRepository.findById(id).orElseThrow(() -> new ReservationNotFoundException("Cannot find any reservations with the given id")), ReservationDto.class);
		
		return new ResponseEntity<>(resDto, HttpStatus.OK);
		
	}
	 
	public ResponseEntity<List<ReservationDto>> getAllForGuest(Long id) throws Exception{
		
		List<ReservationDto> l1 =   reservationRepository.findAll().stream()
				.filter(res -> res.getGuestId() == id)
				.map(res -> mapper.map(res, ReservationDto.class))
				.collect(Collectors.toList());
		
		if(l1.isEmpty())
			throw new ReservationNotFoundException("No reservations were found for the given guest");
		
		return new ResponseEntity<>(l1, HttpStatus.OK);
		
	}
	
	public ResponseEntity<List<ReservationDto>> getAll() throws Exception{
		
		List<ReservationDto> l1 = reservationRepository.findAll().stream()
				.map(res -> mapper.map(res, ReservationDto.class))
				.collect(Collectors.toList());
		
		if(l1.isEmpty())
			throw new ReservationNotFoundException("No reservations were found from the table");
		
		return new ResponseEntity<>(l1, HttpStatus.OK);
		
	}
	
	public ResponseEntity<ReservationDto> edit(Long id, ReservationDto newDto) throws Exception{
		
		ReservationEntity foundReservation = reservationRepository.findById(id).orElseThrow(()-> new ReservationNotFoundException("No reservation was found with the given id"));
		
		foundReservation.setCheck_inDate(newDto.getCheck_inDate());
		foundReservation.setCheck_outDate(newDto.getCheck_outDate());
		foundReservation.setCode(newDto.getCode());
		foundReservation.setNumberofAdults(newDto.getNumberofAdults());
		foundReservation.setNumberOfChildren(newDto.getNumberOfChildren());
		foundReservation.setRoomNumbers(newDto.getRoomNumbers());
		foundReservation.setRoomType(newDto.getRoomType());
		
		foundReservation = reservationRepository.save(foundReservation);
		
		ReservationDto resDto =  mapper.map(foundReservation, ReservationDto.class);
		
		return new ResponseEntity<>(resDto, HttpStatus.OK);
		
	}
	
	public ResponseEntity<String> delete(Long id) throws Exception{
		
		ReservationEntity foundReservation = reservationRepository.findById(id).orElseThrow(()-> new ReservationNotFoundException("No reservation was found with the given id"));
		
		reservationRepository.delete(foundReservation);
		
		return new ResponseEntity<>("Reservation deleted", HttpStatus.OK);
		
	}
	
	public ResponseEntity<String> confirm(String sessionId, String status) throws Exception{
		
		ReservationEntity foundReservation = reservationRepository.findBySessionId(sessionId);
		
		if(foundReservation == null)
			throw new ReservationNotFoundException("Reservation deleted due to expired Payment time");
		
		if(status.equals("Success"))
			foundReservation.setStatus("Booked"); 
		else 
			foundReservation.setStatus("Failed");
		
		reservationRepository.save(foundReservation);
		
		return new ResponseEntity<>("updates made successfully", HttpStatus.OK);
		
		
	}
	
	public ResponseEntity<List<RoomDto>> filter(String checkInDate, String checkOutDate, String roomType) throws Exception{
		// getting all rooms from room service of type roomType
		Set<RoomDto> roomDtos = proxy.filter(roomType).getBody().stream().collect(Collectors.toSet());
		Set<Long> rooms = roomDtos.stream().map(room -> room.getRoomNumber()).collect(Collectors.toSet());
				
		//getting all notAvailable rooms 
		Set<Long> notAvailable = notAvailable(checkInDate, checkOutDate, roomType);
				
		// removing the notAvailable rooms from all rooms of given type
		for(Long i: notAvailable) {
			if(rooms.contains(i))
				rooms.remove(i);
		}
		
		List<RoomDto> result = new ArrayList<>();
		
		for(RoomDto room : roomDtos) {
			if(rooms.contains(room.getRoomNumber()))
					result.add(room);
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
		
	}
	
	public HashSet<Long> notAvailable(String checkInDate, String checkOutDate, String roomType) throws Exception{
		
		
		SimpleDateFormat smf = new SimpleDateFormat("dd/MM/yyyy");
		Date d1 = smf.parse(checkInDate);
		Date d2 = smf.parse(checkOutDate);
		
		List<ReservationEntity> expiredReservations = new ArrayList<>();
		
		List<ReservationEntity> notAvailable = reservationRepository.findAll().stream().filter(reservation -> {
				
				Date currDate1 = null;
				Date currDate2 = null;
				try {
					currDate1 = smf.parse(reservation.getCheck_inDate());
					currDate2 = smf.parse(reservation.getCheck_outDate());

				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				// checking overlapping
				if((currDate1.before(d2) && currDate2.after(d1)) || currDate1.equals(d1) || currDate2.equals(d1) || currDate1.equals(d2) || currDate2.equals(d2))
					return true;
				
				return false;
		}).filter(reservation -> reservation.getRoomType().equals(roomType)).filter(reservation->{
				
				if(reservation.getStatus().equals("Booked"))
					return true;
				if(reservation.getStatus().equals("Pending")) {

					//check the time limit (5 mins for now)
					if(((new Date().getTime() - reservation.getDate().getTime())/(1000*60)) >= 6) {

						expiredReservations.add(reservation);
						return false;
					}
					
					return true;
				}
				
				expiredReservations.add(reservation);
				return false;
		}).collect(Collectors.toList());
		
		HashSet<Long> notAvailableRooms = new HashSet<>();
		
		for(ReservationEntity reservation : notAvailable) {
			for(Long roomNumber: reservation.getRoomNumbers())
				notAvailableRooms.add(roomNumber);
			
		}
		
		//handle expired case here **
		for(ReservationEntity exp: expiredReservations) {
			reservationRepository.delete(exp);;
		}
		
		
		return notAvailableRooms;
		
	}

}
