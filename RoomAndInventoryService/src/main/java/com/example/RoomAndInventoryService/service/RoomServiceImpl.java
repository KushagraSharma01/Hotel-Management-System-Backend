package com.example.RoomAndInventoryService.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
		
		foundRoom.setRoomType(updatedRoom.getRoomType());
		foundRoom.setRoomNumber(updatedRoom.getRoomNumber());
		foundRoom.setPrice(updatedRoom.getPrice());
		
		foundRoom = roomRepository.save(foundRoom);
		
		RoomDto resDto = mapper.map(foundRoom, RoomDto.class);
		
		return new ResponseEntity<>(resDto, HttpStatus.OK);
	}
	
	public ResponseEntity<String> delete(Long id) throws Exception{
		
		RoomEntity foundRoom = roomRepository.findById(id).orElseThrow(()-> new RoomNotFoundException("No room was found with the given id"));
		
		roomRepository.delete(foundRoom);
		
		return new ResponseEntity<>("Room deleted", HttpStatus.OK);
		
	}
	
	public ResponseEntity<List<RoomDto>> filter(String roomType) throws Exception{
		
		
		List<RoomDto> l1 =  roomRepository.findAll().stream().filter(room -> room.getRoomType().equals(roomType)).map(room -> mapper.map(room, RoomDto.class)).collect(Collectors.toList());
		
		
		return new ResponseEntity<>(l1, HttpStatus.OK);
		
	}

	
	
	
}
