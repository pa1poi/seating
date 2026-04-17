package com.pavanseatin.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pavanseatin.demo.entity.Seating;

public interface SeatingRepository extends JpaRepository<Seating, Long> {
    Seating findByRollno(Integer rollno);
}
