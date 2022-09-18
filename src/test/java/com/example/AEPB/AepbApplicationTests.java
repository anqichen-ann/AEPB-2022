package com.example.AEPB;

import com.example.AEPB.exception.ParkingException;
import com.example.AEPB.exception.PickUpException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParkingLotServiceTest {

    private final ParkingLotService parkingLotService = new ParkingLotService();

    @Test
    void should_parking_success_and_get_ticket_when_parking_given_parking_lot_is_not_full_and_vehicle_has_car_plate_number_and_car_plate_number_is_not_duplicated() {
        //given
        String carPlateNumber = "京A2345";

        //when
        String ticket = parkingLotService.parking(carPlateNumber);

        //then
        assertNotNull(ticket);
    }

    @Test
    void should_parking_failed_when_parking_given_parking_lot_is_full() {
        //given
        String carPlateNumber = "京A2345";
        for (int i = 1; i < 101; i++) {
            parkingLotService.parkingLot.add(String.valueOf(i));
        }

        //when
        ParkingException parkingException = assertThrows(ParkingException.class, () -> parkingLotService.parking(carPlateNumber));

        //then
        assertEquals("parking lot is full", parkingException.getMessage());

    }

    @ParameterizedTest
    @NullAndEmptySource
    void should_parking_failed_when_parking_given_parking_lot_is_not_full_and_vehicle_without_car_plate_number(String carPlateNumber) {
        //when
        ParkingException parkingException = assertThrows(ParkingException.class, () -> parkingLotService.parking(carPlateNumber));

        //then
        assertEquals("vehicle has not car plate number", parkingException.getMessage());

    }

    @Test
    void should_parking_failed_when_parking_given_parking_lot_is_not_full_and_car_plate_number_is_duplicated() {
        //given
        String carPlateNumber = "京A2345";
        parkingLotService.parkingLot.add(carPlateNumber);

        //when
        ParkingException parkingException = assertThrows(ParkingException.class, () -> parkingLotService.parking(carPlateNumber));

        //then
        assertEquals("car plate number is duplicated", parkingException.getMessage());

    }

    @Test
    void should_leave_success_when_pick_up_car_given_valid_ticket() {
        //given
        String parkCarPlateNumber = "京A12345";
        parkingLotService.parkingLot.add(parkCarPlateNumber);

        //when
        String carPlateNumber = parkingLotService.pickUp(parkCarPlateNumber);

        //then
        assertEquals(carPlateNumber, parkCarPlateNumber);

    }

    @Test
    void should_leave_failed_when_pick_up_car_given_invalid_ticket() {
        //given
        String parkCarPlateNumber = "京A12345";
        parkingLotService.parkingLot.add(parkCarPlateNumber);
        parkingLotService.pickUp(parkCarPlateNumber);

        //when
        PickUpException pickUpException = assertThrows(PickUpException.class, () -> parkingLotService.pickUp(parkCarPlateNumber));

        //then
        assertEquals("vehicle is not found", pickUpException.getMessage());

    }

    @Test
    void should_leave_failed_when_pick_up_car_given_wrong_ticket() {
        //given
        String wrongCarPlateNumber = "京A12345";

        //when
        PickUpException pickUpException = assertThrows(PickUpException.class, () -> parkingLotService.pickUp(wrongCarPlateNumber));

        //then
        assertEquals("vehicle is not found", pickUpException.getMessage());

    }
}
