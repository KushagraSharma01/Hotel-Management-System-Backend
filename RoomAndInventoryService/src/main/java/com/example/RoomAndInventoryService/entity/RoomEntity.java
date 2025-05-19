package com.example.RoomAndInventoryService.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rooms")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomEntity {
	
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
