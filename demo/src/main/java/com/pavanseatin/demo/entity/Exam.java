package com.pavanseatin.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "exam")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer examId;

    // ⭐ match DB column name EXACTLY
    @Column(name = "sub_code", nullable = false)
    private String subjectCode;

    @Column(name = "subject_name")
    private String subjectName;

    @Column(name = "exam_date")
    private LocalDate examDate;

    @Column(name = "exam_time")
    private String examTime;
}
