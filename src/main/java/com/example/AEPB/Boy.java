package com.example.AEPB;

import com.example.AEPB.exception.ParkingException;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class Boy {

    private final List<ParkingLot> parkingLots;

    public Ticket parking(Car car) {
        ParkingLot parkingLot = findParkingLot();
        parkingLot.parking(car);
        return new Ticket(car.getCarPlateNumber(), parkingLot.getParkingLotNo());
    }

    private ParkingLot findParkingLot() {
        for (ParkingLot parkingLot : parkingLots) {
            if (parkingLot.isNotFull()) {
                return parkingLot;
            }
        }
        throw new ParkingException("parking lot is full");
    }
}
