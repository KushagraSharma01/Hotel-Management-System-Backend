package com.example.StaffService.entity;

import org.springframework.stereotype.Service;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor 
@Table(name = "staffs")
public class StaffEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String code;
	private String name;
	private String address;
	private String nic;
	private Long salary;
	private int age;
	private String occupation;
	private String email;
	
	
	
	public StaffEntity(String code, String name, String address, String nic, Long salary, int age, String occupation,
			String email) {
		super();
		this.code = code;
		this.name = name;
		this.address = address;
		this.nic = nic;
		this.salary = salary;
		this.age = age;
		this.occupation = occupation;
		this.email = email;
	}
	
	
}
