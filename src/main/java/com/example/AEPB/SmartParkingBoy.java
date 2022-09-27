package com.example.AEPB;

import com.example.AEPB.exception.ParkingException;
import com.example.AEPB.exception.PickUpException;
import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class SmartParkingBoy implements ParkingBoy {

    private List<ParkingLot> parkingLots;

    @Override
    public Ticket parking(Car car) {
        checkCarIfExist(car);
        ParkingLot parkingLot = findMaxCapacityParkingLot().orElseThrow(() -> new ParkingException("parking lot is full"));
        parkingLot.parking(car);
        return new Ticket(car.getCarPlateNumber(), parkingLot.getParkingLotNo());
    }

    public void checkCarIfExist(Car car) {
        parkingLots.forEach(parkingLot -> parkingLot.checkCarIfExist(car.getCarPlateNumber()));
    }

    @Override
    public String pickUp(Ticket ticket) {
        ParkingLot foundParkingLot = parkingLots.stream().filter(parkingLot -> ticket.getParkingLotNo().equals(parkingLot.getParkingLotNo()))
                .findFirst().orElseThrow(() -> new PickUpException("parking lot is not found"));
        return foundParkingLot.pickUp(ticket);
    }

    private Optional<ParkingLot> findMaxCapacityParkingLot() {
        return this.parkingLots.stream()
                .filter(ParkingLot::isNotFull)
                .min(Comparator.comparing(ParkingLot::getCarNumber).thenComparing(ParkingLot::getSerialNumber));
    }
}
