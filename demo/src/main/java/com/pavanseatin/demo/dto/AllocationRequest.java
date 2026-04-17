package com.pavanseatin.demo.dto;

import lombok.Data;

@Data
public class AllocationRequest {

    private Integer semester;
    private Integer year;
    private String course;
    private String examType;
    private Integer studentsPerBench;

}