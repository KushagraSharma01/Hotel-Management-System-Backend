package com.example.RoomAndInventoryService.entity;

import java.util.HashMap;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rooms")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomEntity {
<<<<<<< HEAD
 
=======

>>>>>>> 7cd6b6941e801dc5b2cf4da7f8f3de81aaa09964
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long roomNumber;
	
	private String roomType;
	
	private Long price;
	
	
	public RoomEntity(Long roomNumber, String roomType,String price) {
		super();
		this.roomNumber = roomNumber;
		this.roomType = roomType;
	}
	
	
}
