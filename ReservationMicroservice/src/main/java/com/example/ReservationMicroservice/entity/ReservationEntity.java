package com.example.ReservationMicroservice.entity;

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
@Table(name = "reservations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private Long guestId;
	
	private String Code;
	private Long numberOfChildren;
	private Long numberofAdults;
	private String check_inDate;
	private String check_outDate;
	private String roomType;
	private List<Long> roomNumbers;
	
	
	
	public ReservationEntity(Long guestId, String code, Long numberOfChildren, Long numberofAdults, String check_inDate,
			String check_outDate, String roomType, List<Long> roomNumbers) {
		super();
		this.guestId = guestId;
		Code = code;
		this.numberOfChildren = numberOfChildren;
		this.numberofAdults = numberofAdults;
		this.check_inDate = check_inDate;
		this.check_outDate = check_outDate;
		this.roomType = roomType;
		this.roomNumbers = roomNumbers;
	}
	
	
	
}
