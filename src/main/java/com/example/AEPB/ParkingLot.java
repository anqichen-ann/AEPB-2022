package com.example.AEPB;

import com.example.AEPB.exception.ParkingException;
import com.example.AEPB.exception.PickUpException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ParkingLot {

    private static final int MAX_PARKING_LOT_CAPACITY = 100;

    private final List<Car> carList = new ArrayList<>();

    private String ParkingLotNo = "A";

    private int serialNumber;

    public Ticket parking(Car car) {
        car.checkCarPlateNumberValid();
        checkParkingLotIsFull();
        if (isCarExist(car.getCarPlateNumber())) {
            throw new ParkingException("car plate number is duplicated");
        }
        carList.add(car);
        return new Ticket(car.getCarPlateNumber());
    }

    public String pickUp(Ticket ticket) {
        if (!isCarExist(ticket.getCarPlateNumber())) {
            throw new PickUpException("vehicle is not found");
        }
        carList.removeIf(car -> ticket.getCarPlateNumber().equals(car.getCarPlateNumber()));
        return ticket.getCarPlateNumber();
    }

    private void checkParkingLotIsFull() {
        if (carList.size() >= MAX_PARKING_LOT_CAPACITY) {
            throw new ParkingException("parking lot is full");
        }
    }

    private boolean isCarExist(String carPlateNumber) {
        return carList.stream()
                .map(Car::getCarPlateNumber)
                .collect(Collectors.toList())
                .contains(carPlateNumber);
    }

    public boolean isNotFull() {
        return carList.size() < MAX_PARKING_LOT_CAPACITY;
    }
}