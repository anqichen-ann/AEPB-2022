package com.example.AEPB;

import com.example.AEPB.exception.ParkingException;
import com.example.AEPB.exception.PickUpException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ParkingLot {

    private  int parkingLotCapacity;

    private final List<Car> carList = new ArrayList<>();

    private String ParkingLotNo = "A";

    private int serialNumber;

    public Ticket parking(Car car) {
        car.checkCarPlateNumberValid();
        checkParkingLotIsFull();
        checkCarIfExist(car.getCarPlateNumber());
        carList.add(car);
        return new Ticket(car.getCarPlateNumber());
    }

    public void checkCarIfExist(String carPlateNumber) {
        if (isCarExist(carPlateNumber)) {
            throw new ParkingException("car plate number is duplicated");
        }
    }

    public String pickUp(Ticket ticket) {
        if (!isCarExist(ticket.getCarPlateNumber())) {
            throw new PickUpException("vehicle is not found");
        }
        carList.removeIf(car -> ticket.getCarPlateNumber().equals(car.getCarPlateNumber()));
        return ticket.getCarPlateNumber();
    }

    private void checkParkingLotIsFull() {
        if (carList.size() >= parkingLotCapacity) {
            throw new ParkingException("parking lot is full");
        }
    }

    public boolean isCarExist(String carPlateNumber) {
        return carList.stream()
                .map(Car::getCarPlateNumber)
                .collect(Collectors.toList())
                .contains(carPlateNumber);
    }

    public boolean isNotFull() {
        return carList.size() < parkingLotCapacity;
    }

    public int getCarNumber() {
        return carList.size();
    }

    public BigDecimal getOccupancyRate() {
        return BigDecimal.valueOf(carList.size()).divide(BigDecimal.valueOf(parkingLotCapacity), 2, RoundingMode.HALF_UP);
    }
}