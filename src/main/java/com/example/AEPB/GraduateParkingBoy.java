package com.example.AEPB;

import com.example.AEPB.exception.ParkingException;
import com.example.AEPB.exception.PickUpException;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class GraduateParkingBoy implements ParkingBoy{

    private List<ParkingLot> parkingLots;

    public GraduateParkingBoy(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots.stream()
                .sorted(Comparator.comparing(ParkingLot::getSerialNumber))
                .collect(Collectors.toList());
    }

    public Ticket parking(Car car) {
        checkCarIfExist(car);
        ParkingLot parkingLot = findParkingLot();
        parkingLot.parking(car);
        return new Ticket(car.getCarPlateNumber(), parkingLot.getParkingLotNo());
    }

    private ParkingLot findParkingLot() {
        return parkingLots.stream()
                .filter(ParkingLot::isNotFull)
                .findFirst()
                .orElseThrow(() -> new ParkingException("parking lot is full"));
    }

    public void checkCarIfExist(Car car) {
        parkingLots.forEach(parkingLot -> parkingLot.checkCarIfExist(car.getCarPlateNumber()));
    }

    public String pickUp(Ticket ticket) {
        ParkingLot foundParkingLot = parkingLots.stream().filter(parkingLot -> ticket.getParkingLotNo().equals(parkingLot.getParkingLotNo()))
                .findFirst().orElseThrow(() -> new PickUpException("parking lot is not found"));
        return foundParkingLot.pickUp(ticket);
    }
}
