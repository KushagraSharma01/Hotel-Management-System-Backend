package com.example.AuthService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AuthService.entity.Role;
import com.example.AuthService.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
	
	UserEntity findByEmail(String email);
	
	UserEntity findByFirstName(String firstName);
	
	UserEntity findByEmailAndRole(String email, Role role);

}
