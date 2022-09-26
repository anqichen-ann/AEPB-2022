package com.example.AEPB;

import com.example.AEPB.exception.ParkingException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SmartGraduateParkingBoy extends GraduateParkingBoy {

    public SmartGraduateParkingBoy(List<ParkingLot> parkingLots) {
        super(parkingLots);
    }

    @Override
    public Ticket parking(Car car) {
        checkCarIfExist(car);
        ParkingLot parkingLot = findMaxCapacityParkingLot().orElseThrow(() -> new ParkingException("parking lot is full"));
        System.out.println("parking lot:" + parkingLot.getParkingLotNo());
        parkingLot.parking(car);
        return new Ticket(car.getCarPlateNumber(), parkingLot.getParkingLotNo());
    }

    private Optional<ParkingLot> findMaxCapacityParkingLot() {
        return this.parkingLots.stream()
                .filter(ParkingLot::isNotFull)
                .min(Comparator.comparing(ParkingLot::getCarNumber).thenComparing(ParkingLot::getSerialNumber));
    }
}
