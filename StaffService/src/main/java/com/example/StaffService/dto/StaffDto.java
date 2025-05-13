package com.example.StaffService.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffDto {
 
	private Long id;
	
	@NotNull(message = "code cannot be null")
	private String code;
	
	@NotNull(message = "name cannot be null")
	@Pattern(regexp = "^[A-Z]{1}[a-z]{2,} [A-Z]{1}[a-z]{2,}$", message="Full name is not in the correct format")
	private String name;
	
	@NotNull(message = "address cannot be null")
	private String address;
	
	@NotNull(message = "nic cannot be null")
	private String nic;
	
	@NotNull(message = "salary cannot be null")
	private Long salary;
	
	@NotNull(message = "age cannot be null")
	private int age;
	
	@NotNull(message = "occupation cannot be null")
	private String occupation;
	
	@NotNull(message = "email cannot be null")
	@Email(message = "email format is not correct")
	private String email;
	
	
	
}
