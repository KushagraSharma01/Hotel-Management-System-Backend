package com.example.ReservationMicroservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ReservationMicroservice.dto.BookingDto;
import com.example.ReservationMicroservice.dto.ReservationDto;
import com.example.ReservationMicroservice.dto.RoomDto;
import com.example.ReservationMicroservice.entity.ReservationEntity;
import com.example.ReservationMicroservice.exceptions.ReservationNotFoundException;
import com.example.ReservationMicroservice.exceptions.RoomsNotAvailableException;
import com.example.ReservationMicroservice.repository.ReservationRepository;

@Service
public class ReservationServiceImpl implements ReservationService{

	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired 
	private ModelMapper mapper;
	 
	@Autowired
	private RoomServiceProxy proxy;
	
	public ResponseEntity<ReservationDto> create(ReservationDto inDto) throws Exception{
		
		ReservationEntity newReservation = mapper.map(inDto, ReservationEntity.class);
		
//		checking if the rooms selected are available or not
		List<Long> rooms = proxy.filter(inDto.getCheck_inDate(), inDto.getCheck_outDate(), inDto.getRoomType()).getBody().stream().map(room -> room.getRoomNumber()).collect(Collectors.toList());
		boolean available = true;
		
		for(Long i: inDto.getRoomNumbers()) {
			if(!rooms.contains(i)) {
				available = false;
				break;
			}
		}
		
		if(!available || rooms.isEmpty())
			throw new RoomsNotAvailableException("Rooms not available for reservation");
		
///		booking rooms from the room service
		proxy.book(new BookingDto(inDto.getRoomNumbers(), inDto.getCheck_inDate(), inDto.getCheck_outDate(), inDto.getGuestId()));
		
		newReservation = reservationRepository.save(newReservation);
		
		ReservationDto resDto =  mapper.map(newReservation, ReservationDto.class);
		
		return new ResponseEntity<>(resDto, HttpStatus.OK);
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
	
	public ResponseEntity<List<RoomDto>> filter(String checkInDate, String checkOutDate, String roomType) throws Exception{
		return proxy.filter(checkInDate, checkOutDate, roomType);
	}

}
