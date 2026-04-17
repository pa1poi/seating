package com.pavanseatin.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
@Data
public class Student {

    @Id
    private Integer rollno;

    private Double attendance;
    private String course;
    private String branch;
    private Integer year;
    private Integer semester;
    private String mailId;
    private Boolean eligible;
}
