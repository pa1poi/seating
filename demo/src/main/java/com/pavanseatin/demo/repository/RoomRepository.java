package com.pavanseatin.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pavanseatin.demo.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}
