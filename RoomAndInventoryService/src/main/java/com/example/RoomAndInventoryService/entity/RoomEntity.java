package com.example.RoomAndInventoryService.entity;

import java.util.HashMap;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long roomNumber;
	
	private List<Long> guestIds;
	
	private HashMap<String, String> dates;
	private String roomType;
	
	
	public RoomEntity(List<Long> guestIds, Long roomNumber, HashMap<String, String> dates, String roomType) {
		super();
		this.guestIds = guestIds;
		this.roomNumber = roomNumber;
		this.dates = dates;
		this.roomType = roomType;
	}
	
	
}
