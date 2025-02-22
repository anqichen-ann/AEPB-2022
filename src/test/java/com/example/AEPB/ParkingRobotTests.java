package com.example.AEPB;

import com.example.AEPB.exception.ParkingException;
import com.example.AEPB.exception.PickUpException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParkingRobotTests {

    private ParkingRobot parkingRobot;
    private ParkingLot parkingLotA;
    private ParkingLot parkingLotB;
    private ParkingLot parkingLotC;
    private static final String CAR_PLATE_NUMBER = "京A12345";

    @BeforeEach
    void setUp() {
        parkingLotA = new ParkingLot(100, "A", 1);
        parkingLotB = new ParkingLot(100, "B", 2);
        parkingLotC = new ParkingLot(100, "C", 3);
        parkingRobot = new ParkingRobot(List.of(parkingLotA, parkingLotB, parkingLotC));
    }

    @Test
    void should_parking_in_A_and_get_ticket_when_parking_given_A_B_C_is_empty() {
        //given
        Car car = new Car(CAR_PLATE_NUMBER);

        //when
        Ticket ticket = parkingRobot.parking(car);

        //then
        assertEquals(CAR_PLATE_NUMBER, ticket.getCarPlateNumber());
        assertEquals("A", ticket.getParkingLotNo());
    }

    @Test
    void should_parking_in_B_and_get_ticket_when_parking_given_parking_space_A_with_98_percent_and_B_with_99_percent_and_C_with_99_percent() {
        //given
        Car car = new Car(CAR_PLATE_NUMBER);
        String carPlateNumber = "京C12345";
        parkingLotA.parking(car);
        parkingLotA.parking(new Car("京A123451"));
        parkingLotB.parking(new Car("京A123452"));
        parkingLotC.parking(new Car("京A123453"));

        //when
        Ticket ticket = parkingRobot.parking(new Car(carPlateNumber));

        //then
        assertEquals(carPlateNumber, ticket.getCarPlateNumber());
        assertEquals("B", ticket.getParkingLotNo());
    }

    @Test
    void should_parking_failed_when_parking_given_parking_lot_A_and_B_and_C_all_full() {
        //given
        Car car = new Car(CAR_PLATE_NUMBER);
        String carPlateNumberA = "京A12345";
        String carPlateNumberB = "京B12345";
        String carPlateNumberC = "京C12345";
        for (int i = 1; i < 101; i++) {
            parkingLotA.parking(new Car(carPlateNumberA + i));
            parkingLotB.parking(new Car(carPlateNumberB + i));
            parkingLotC.parking(new Car(carPlateNumberC + i));
        }

        //when
        ParkingException parkingException = assertThrows(ParkingException.class, () -> parkingRobot.parking(car));

        //then
        assertEquals("parking lot is full", parkingException.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void should_parking_failed_when_parking_given_parking_lot_is_not_full_and_car_plate_number_is_empty(String carPlateNumber) {
        //given
        Car car = new Car(carPlateNumber);

        //when
        ParkingException parkingException = assertThrows(ParkingException.class, () -> parkingRobot.parking(car));

        //then
        assertEquals("vehicle has not car plate number", parkingException.getMessage());
    }

    @Test
    void should_parking_failed_when_parking_given_parking_lot_is_not_full_and_car_plate_number_is_exist_in_B() {
        //given
        Car car = new Car(CAR_PLATE_NUMBER);
        parkingLotB.parking(car);

        //when
        ParkingException parkingException = assertThrows(ParkingException.class, () -> parkingRobot.parking(car));

        //then
        assertEquals("car plate number is duplicated", parkingException.getMessage());
    }


}
