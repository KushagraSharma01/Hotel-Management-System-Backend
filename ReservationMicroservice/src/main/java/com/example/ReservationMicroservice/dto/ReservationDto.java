package com.example.ReservationMicroservice.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
	
	private Long id; 
	
	@Min(1)
	private Long guestId;
	
	@NotNull(message = "Code cannot be null")
	private String Code;
	
	@NotNull(message = "numberOfChildren cannot be null")
	private Long numberOfChildren;
	
	@NotNull(message = "numberofAdults cannot be null")
	private Long numberofAdults;
	
	@NotNull(message = "check in date cannot be null")
	private String check_inDate;
	
	@NotNull(message = "check out date cannot be null")
	private String check_outDate;
	
	@Pattern(regexp = "^(AC)|(Non-AC)$", message = "Room type can either be AC or Non-AC")
	private String roomType;
	
	@NotNull(message = "room Numbers cannot be null")
	private List<Long> roomNumbers;
	
	private String sessionId;
	
	private String status;
	
	private Date date;
	

	public void setCheck_inDate(String check_inDate) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			Date inDate = sdf.parse(check_inDate);
			
			if(inDate.before(new Date()))
				throw new Exception("check in date cannot be before current date");
			
		}	
		catch(ParseException e) {
			throw new Exception("Invalid date format (format should be dd/MM/yyyy)");
		}
		
		this.check_inDate = check_inDate;
		
	}



	public void setCheck_outDate(String check_outDate) throws Exception{
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
		try {
			Date inDate = sdf.parse(check_outDate);
			
			if(inDate.before(new Date()))
				throw new Exception("check out date cannot be before current date");
			
			if(sdf.parse(check_inDate).after(inDate))
				throw new Exception("check out date cannot be before check in date");
		}	
		catch(ParseException e) {
			throw new Exception("Invalid date format (format should be dd/MM/yyyy)");
		}
		
		this.check_outDate = check_outDate;
	}



	
	
	
		
}
