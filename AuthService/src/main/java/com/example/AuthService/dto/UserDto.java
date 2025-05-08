package com.example.AuthService.dto;

import com.example.AuthService.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private Long id;
	
	@NotNull(message = "firstname cannot be null")
	@Pattern(regexp = "^[A-Z]{1}[a-z]{2,}$", message = "firstname format is incorrect")
	private String firstName;
	
	@NotNull(message = "lastname cannot be null")
	@Pattern(regexp = "^[A-Z]{1}[a-z]{2,}$", message = "lastname format is incorrect")
	private String lastName;
	
	@NotNull(message = "email cannot be null")
	@Email(message = "incorrect email format")
	private String email;
	
	@NotNull(message = "password cannot be null")
	@Pattern(regexp = "^(?=.*[A-Z]{1})(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&]{1})[a-zA-Z0-9!@#$%^&]{8,}$", message="Invalid Password Format")
	private String password;
	
	private Role role;

	public UserDto(String firstName, String lastName, String email, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}
	
	
	
}
