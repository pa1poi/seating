package com.pavanseatin.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pavanseatin.demo.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, String> {
}
