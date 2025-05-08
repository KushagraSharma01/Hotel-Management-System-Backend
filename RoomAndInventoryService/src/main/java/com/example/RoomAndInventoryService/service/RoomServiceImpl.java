package com.example.RoomAndInventoryService.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.RoomAndInventoryService.dto.BookingDto;
import com.example.RoomAndInventoryService.dto.RoomDto;
import com.example.RoomAndInventoryService.entity.RoomEntity;
import com.example.RoomAndInventoryService.exceptions.RoomAlreadyPresentException;
import com.example.RoomAndInventoryService.exceptions.RoomNotFoundException;
import com.example.RoomAndInventoryService.repository.RoomRepository;

@Service
public class RoomServiceImpl implements RoomService{

	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	public ResponseEntity<List<RoomDto>> getAll() throws Exception{
		
		List<RoomDto> l1 = roomRepository.findAll().stream()
												   .map(room -> mapper.map(room, RoomDto.class))
												   .collect(Collectors.toList());
		
		if(l1.isEmpty())
			throw new RoomNotFoundException("No rooms were found from the table");
		
		return new ResponseEntity<>(l1, HttpStatus.OK);
		
	}
	
	public ResponseEntity<RoomDto> create(RoomDto newRoom) throws Exception{
		
		if(roomRepository.findByRoomNumber(newRoom.getRoomNumber()) != null)
			throw new RoomAlreadyPresentException("Room with the given room number is already present");
		
		RoomEntity newRoomEntity = mapper.map(newRoom, RoomEntity.class);
		
		newRoomEntity = roomRepository.save(newRoomEntity);
		
		RoomDto resDto = mapper.map(newRoomEntity, RoomDto.class);
		
		return new ResponseEntity<>(resDto, HttpStatus.OK);
		
	}
	
	public ResponseEntity<RoomDto> edit(Long id, RoomDto updatedRoom) throws Exception{
		
		RoomEntity foundRoom = roomRepository.findById(id).orElseThrow(()-> new RoomNotFoundException("No room was found with the given id"));
		
		foundRoom.setDates(updatedRoom.getDates());
		foundRoom.setRoomType(updatedRoom.getRoomType());
		foundRoom.setRoomNumber(updatedRoom.getRoomNumber());
		
		foundRoom = roomRepository.save(foundRoom);
		
		RoomDto resDto = mapper.map(foundRoom, RoomDto.class);
		
		return new ResponseEntity<>(resDto, HttpStatus.OK);
	}
	
	public ResponseEntity<String> delete(Long id) throws Exception{
		
		RoomEntity foundRoom = roomRepository.findById(id).orElseThrow(()-> new RoomNotFoundException("No room was found with the given id"));
		
		roomRepository.delete(foundRoom);
		
		return new ResponseEntity<>("Room deleted", HttpStatus.OK);
		
	}
	
	public ResponseEntity<List<RoomDto>> filter(String checkInDate, String checkOutDate, String roomType) throws Exception{
		
		SimpleDateFormat smf = new SimpleDateFormat("dd/MM/yyyy");
		
		List<RoomDto> l1 =  roomRepository.findAll().stream().filter(room ->{
			
				boolean isAvail = false;
				
				if(room.getDates().containsKey(checkInDate) || room.getDates().containsKey(checkOutDate))
					return false;
				
				try {
					Date dt1 = smf.parse(checkInDate);
					Date dt2 = smf.parse(checkOutDate);
					
					for(String inDate: room.getDates().keySet()) {
						if(smf.parse(inDate).before(dt1)&&smf.parse(room.getDates().get(inDate)).after(dt1))
							return false;
						if(smf.parse(inDate).before(dt2)&&smf.parse(room.getDates().get(inDate)).after(dt2))
							return false;
					}
				}
				catch(Exception e) {
					System.out.println(e.getMessage());
				}
				
				return true;
		}).filter(room -> room.getRoomType().equals(roomType)).map(room -> mapper.map(room, RoomDto.class)).collect(Collectors.toList());
		
		
		return new ResponseEntity<>(l1, HttpStatus.OK);
		
	}
	
	public ResponseEntity<List<RoomDto>> bookRooms(BookingDto bookDto) throws Exception{
		
		
		
		//updating the saving status of the rooms in the repository
		roomRepository.findAll().stream().filter(room -> {
			for(Long num: bookDto.getRoomNumbers()) {
				if(num.equals(room.getRoomNumber()))
					return true;
			}
			return false;
		}).forEach(room -> {
			room.getDates().put(bookDto.getCheckInDate(), bookDto.getCheckOutDate());
			if(!room.getGuestIds().contains(bookDto.getGuestId()))
				room.getGuestIds().add(bookDto.getGuestId());
			roomRepository.save(room);
		});
		
		List<RoomDto> l1 = roomRepository.findAll().stream().filter(room -> {
			for(Long num: bookDto.getRoomNumbers()) {
				if(num.equals(room.getRoomNumber()))
					return true;
			}
			return false;
		}).map(room -> mapper.map(room, RoomDto.class)).collect(Collectors.toList());
		
		if(l1.isEmpty())
			throw new RoomNotFoundException("No rooms were Booked");
		
		return new ResponseEntity<>(l1, HttpStatus.OK);
	}
	
}
