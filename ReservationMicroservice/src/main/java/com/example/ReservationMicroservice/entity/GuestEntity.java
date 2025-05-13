package com.example.ReservationMicroservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "guests")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GuestEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String memberCode;
	
	private String PhoneNumber;
	private String Company;

	private String name;
	
	private String email;
	private String gender;
	private String address;
	
	
	public GuestEntity(String memberCode, String phoneNumber, String company, String name, String email, String gender,
			String address) {
		super();
		this.memberCode = memberCode;
		PhoneNumber = phoneNumber;
		Company = company;
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.address = address;
	}
	
	
	
}
