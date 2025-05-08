package com.example.AuthService.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDto {

	@NotNull(message = "email cannot be null")
	@Email(message = "incorrect email format")
	private String email;
	
	@NotNull(message = "password cannot be null")
	private String password;
	
}
