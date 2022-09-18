package com.example.AEPB;

import com.example.AEPB.exception.ParkingException;
import com.example.AEPB.exception.PickUpException;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLotService {

    private static final int MAX_PARKING_LOT_CAPACITY = 100;

    public final List<String> parkingLot = new ArrayList<>();

    public String parking(String carPlateNumber) {
        if (StringUtils.isBlank(carPlateNumber)) {
            throw new ParkingException("vehicle has not car plate number");
        }
        if (parkingLot.size() >= MAX_PARKING_LOT_CAPACITY) {
            throw new ParkingException("parking lot is full");
        }
        if (parkingLot.contains(carPlateNumber)) {
            throw new ParkingException("car plate number is duplicated");
        }
        parkingLot.add(carPlateNumber);
        return carPlateNumber;
    }

    public String pickUp(String carPlateNumber) {
        if (!parkingLot.contains(carPlateNumber)) {
            throw new PickUpException("vehicle is not found");
        }
        parkingLot.remove(carPlateNumber);
        return carPlateNumber;
    }
}