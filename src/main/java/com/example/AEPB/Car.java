package com.example.AEPB;

import com.example.AEPB.exception.ParkingException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor
public class Car {

    private String carPlateNumber;

    public void checkCarPlateNumberValid() {
        if (StringUtils.isBlank(carPlateNumber)) {
            throw new ParkingException("vehicle has not car plate number");
        }
    }

}
