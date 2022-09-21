package com.example.AEPB;

import com.example.AEPB.exception.ParkingException;
import com.example.AEPB.exception.PickUpException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingLot {

    private static final int MAX_PARKING_LOT_CAPACITY = 100;

    public final List<Car> parkingLot = new ArrayList<>();

    public Ticket parking(Car car) {
        car.checkCarPlateNumberValid();
        checkParkingLotIsFull();
        if (isCarExist(car.getCarPlateNumber())) {
            throw new ParkingException("car plate number is duplicated");
        }
        parkingLot.add(car);
        return new Ticket(car.getCarPlateNumber());
    }

    public String pickUp(Ticket ticket) {
        if (!isCarExist(ticket.getCarPlateNumber())) {
            throw new PickUpException("vehicle is not found");
        }
        parkingLot.removeIf(car -> ticket.getCarPlateNumber().equals(car.getCarPlateNumber()));
        return ticket.getCarPlateNumber();
    }

    private void checkParkingLotIsFull() {
        if (parkingLot.size() >= MAX_PARKING_LOT_CAPACITY) {
            throw new ParkingException("parking lot is full");
        }
    }

    private boolean isCarExist(String carPlateNumber) {
        return parkingLot.stream()
                .map(Car::getCarPlateNumber)
                .collect(Collectors.toList())
                .contains(carPlateNumber);
    }


}