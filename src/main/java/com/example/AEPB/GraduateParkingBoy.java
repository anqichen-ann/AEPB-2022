package com.example.AEPB;

import com.example.AEPB.exception.ParkingException;
import com.example.AEPB.exception.PickUpException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class GraduateParkingBoy {

    private List<ParkingLot> parkingLots;

    public Ticket parking(Car car) {
        checkCarIfExist(car);
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

    public void checkCarIfExist(Car car) {
        parkingLots.forEach(parkingLot -> parkingLot.checkCarIfExist(car.getCarPlateNumber()));
    }

    public String pickUp(Ticket ticket) {
        for (ParkingLot parkingLot : parkingLots) {
            if (ticket.getParkingLotNo().equals(parkingLot.getParkingLotNo())) {
                return parkingLot.pickUp(ticket);
            }
        }
        throw new PickUpException("parking lot is not found");
    }
}
