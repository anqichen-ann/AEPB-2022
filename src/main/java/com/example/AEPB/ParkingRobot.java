package com.example.AEPB;

import com.example.AEPB.exception.ParkingException;
import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ParkingRobot {

    private List<ParkingLot> parkingLots;

    public Ticket parking(Car car) {
        checkCarIfExist(car);
        ParkingLot parkingLot = findMaxVacancyRateParkingLot().orElseThrow(() -> new ParkingException("parking lot is full"));
        parkingLot.parking(car);
        return new Ticket(car.getCarPlateNumber(), parkingLot.getParkingLotNo());
    }

    public void checkCarIfExist(Car car) {
        parkingLots.forEach(parkingLot -> parkingLot.checkCarIfExist(car.getCarPlateNumber()));
    }

    private Optional<ParkingLot> findMaxVacancyRateParkingLot() {
        return this.parkingLots.stream()
                .filter(ParkingLot::isNotFull)
                .min(Comparator.comparing(ParkingLot::getOccupancyRate).thenComparing(ParkingLot::getSerialNumber));
    }
}
