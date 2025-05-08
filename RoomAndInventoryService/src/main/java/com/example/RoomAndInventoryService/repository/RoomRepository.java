package com.example.RoomAndInventoryService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RoomAndInventoryService.entity.RoomEntity;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long>{

	public RoomEntity findByRoomNumber(Long roomNumber);
	
}
