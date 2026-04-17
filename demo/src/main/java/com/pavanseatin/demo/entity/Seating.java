package com.pavanseatin.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Seating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rollno;
    private Integer roomNo;

    private Integer rowNo;
    private Integer benchNo;
    private Integer seatNo;

    private String side;   // ⭐ ADD THIS (LEFT/RIGHT/CENTER)
}
