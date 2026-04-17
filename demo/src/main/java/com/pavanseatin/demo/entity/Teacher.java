package com.pavanseatin.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "teacher")
public class Teacher {

    @Id
    private String teacherCode;   // PRIMARY KEY

    private String name;
    private String email;
    private String department;
    private String phone;
}
