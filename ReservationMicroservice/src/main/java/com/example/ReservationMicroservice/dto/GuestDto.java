package com.example.ReservationMicroservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuestDto {
	private Long id; 

	@NotNull(message = "memberCode cannot be null")
	private String memberCode;
	
	@Pattern(regexp = "^[0-9]{10}$")
	private String PhoneNumber;
	
	@NotNull(message = "Company cannot be null")
	private String Company;
	
	@Pattern(regexp = "^[A-Z]{1}.{3,} [A-Z]{1}.{3,}$", message = "not the right fullname format")
	private String name;
	
	@Email
	@NotNull(message = "email cannot be null")
	private String email;
	
	@NotNull(message = "gender cannot be null")
	private String gender;
	@NotNull(message = "address cannot be null")
	private String address;
	
	
	
	
}
