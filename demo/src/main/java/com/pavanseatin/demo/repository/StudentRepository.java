package com.pavanseatin.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pavanseatin.demo.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findByAttendanceGreaterThan(Float attendance);

    List<Student> findBySemester(Integer semester);

    List<Student> findByAttendanceGreaterThanEqual(double attendance);

    List<Student> findBySemesterAndYearAndCourse(
            Integer semester,
            Integer year,
            String course
    );
}