package com.pavanseatin.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pavanseatin.demo.entity.TeacherAllocation;

public interface TeacherAllocationRepository
        extends JpaRepository<TeacherAllocation, Long> {

    List<TeacherAllocation> findByTeacher_TeacherCode(String teacherCode);
}
