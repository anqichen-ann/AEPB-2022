package com.example.AEPB;

import com.example.AEPB.exception.ParkingException;
import com.example.AEPB.exception.PickUpException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParkingLotTest {

    private final ParkingLot parkingLot = new ParkingLot();

    @Test
    void should_parking_success_and_get_ticket_when_parking_given_parking_lot_is_not_full_and_vehicle_has_car_plate_number_and_car_plate_number_is_not_duplicated() {
        //given
        String carPlateNumber = "京A2345";
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car(carPlateNumber);

        //when
        Ticket ticket = parkingLot.parking(car);

        //then
        assertNotNull(ticket);
        assertEquals(ticket.getCarPlateNumber(), car.getCarPlateNumber());
    }

    @Test
    void should_parking_failed_when_parking_given_parking_lot_is_full() {
        //given
        String carPlateNumber = "京A2345";
        ParkingLot parkingLot = new ParkingLot();
        for (int i = 1; i < 101; i++) {
            parkingLot.parkingLot.add(new Car(carPlateNumber + i));
        }
        Car car = new Car("京A2345678");

        //when
        ParkingException parkingException = assertThrows(ParkingException.class, () -> parkingLot.parking(car));

        //then
        assertEquals("parking lot is full", parkingException.getMessage());

    }

    @ParameterizedTest
    @NullAndEmptySource
    void should_parking_failed_when_parking_given_parking_lot_is_not_full_and_vehicle_without_car_plate_number(String carPlateNumber) {
        //when
        ParkingException parkingException = assertThrows(ParkingException.class, () -> parkingLot.parking(new Car(carPlateNumber)));

        //then
        assertEquals("vehicle has not car plate number", parkingException.getMessage());

    }

    @Test
    void should_parking_failed_when_parking_given_parking_lot_is_not_full_and_car_plate_number_is_duplicated() {
        //given
        String carPlateNumber = "京A2345";
        Car car = new Car(carPlateNumber);
        parkingLot.parkingLot.add(car);

        //when
        ParkingException parkingException = assertThrows(ParkingException.class, () -> parkingLot.parking(car));

        //then
        assertEquals("car plate number is duplicated", parkingException.getMessage());

    }

    @Test
    void should_leave_success_when_pick_up_car_given_valid_ticket() {
        //given
        String parkCarPlateNumber = "京A12345";
        Car car = new Car(parkCarPlateNumber);
        parkingLot.parkingLot.add(car);
        Ticket ticket = new Ticket(parkCarPlateNumber);

        //when
        String carPlateNumber = parkingLot.pickUp(ticket);

        //then
        assertEquals(carPlateNumber, parkCarPlateNumber);

    }

    @Test
    void should_leave_failed_when_pick_up_car_given_invalid_ticket() {
        //given
        String parkCarPlateNumber = "京A12345";
        Car car = new Car(parkCarPlateNumber);
        parkingLot.parkingLot.add(car);
        Ticket ticket = new Ticket(parkCarPlateNumber);
        parkingLot.pickUp(ticket);

        //when
        PickUpException pickUpException = assertThrows(PickUpException.class, () -> parkingLot.pickUp(ticket));

        //then
        assertEquals("vehicle is not found", pickUpException.getMessage());

    }

    @Test
    void should_leave_failed_when_pick_up_car_given_wrong_ticket() {
        //given
        String wrongCarPlateNumber = "京A12345";
        Ticket ticket = new Ticket(wrongCarPlateNumber);

        //when
        PickUpException pickUpException = assertThrows(PickUpException.class, () -> parkingLot.pickUp(ticket));

        //then
        assertEquals("vehicle is not found", pickUpException.getMessage());

    }

    @Test
    void should_parking_in_A_and_get_ticket_when_parking_given_A_is_not_full() {
        //given

//        String carPlateNumber = "京A12345";
//        Car car = new Car(carPlateNumber);
//        ParkingLot parkingLotA = new ParkingLot();
//        ParkingLot parkingLotB = new ParkingLot();
//        ParkingLot parkingLotC = new ParkingLot();
//        Boy boy = new Boy();
//
//        Ticket ticket = boy.parking(car);
//
//        assertEquals(carPlateNumber, ticket.getCarPlateNumber());
//        assertEquals("A", ticket.getParkingLotNo());
//        assertEquals(1, parkingLotA.size());
    }
}
