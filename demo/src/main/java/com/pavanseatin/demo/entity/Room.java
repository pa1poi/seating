package com.pavanseatin.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Room {

    @Id
    private Integer roomNo;

    private Integer rowsCount;
    private Integer benchesPerRow;
    private Integer seatsPerBench;
    private Boolean occupStat;
}
