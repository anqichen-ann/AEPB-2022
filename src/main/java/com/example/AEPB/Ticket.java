package com.example.AEPB;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Ticket {

    private String carPlateNumber;

    private String parkingLotNo;


    public Ticket(String carPlateNumber) {
        this.carPlateNumber = carPlateNumber;
    }
}
