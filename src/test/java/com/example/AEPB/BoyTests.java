package com.example.AEPB;

import com.example.AEPB.exception.ParkingException;
import com.example.AEPB.exception.PickUpException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BoyTests {

    @Test
    void should_parking_in_A_and_get_ticket_when_parking_given_A_is_not_full() {
        //given
        ParkingLot parkingLotA = new ParkingLot("A", 1);
        ParkingLot parkingLotB = new ParkingLot("B", 2);
        ParkingLot parkingLotC = new ParkingLot("C", 3);
        List<ParkingLot> parkingLots = Stream.of(parkingLotA, parkingLotB, parkingLotC)
                .sorted(Comparator.comparing(ParkingLot::getSerialNumber))
                .collect(Collectors.toList());
        String carPlateNumber = "京A12345";
        Car car = new Car(carPlateNumber);
        Boy boy = new Boy(parkingLots);

        //when
        Ticket ticket = boy.parking(car);

        //then
        assertEquals(carPlateNumber, ticket.getCarPlateNumber());
        assertEquals("A", ticket.getParkingLotNo());
        assertEquals(1, parkingLotA.getCarList().size());
    }

    @Test
    void should_parking_in_B_and_get_ticket_when_parking_given_A_is_full_and_B_is_not_full() {
        //given
        ParkingLot parkingLotA = new ParkingLot("A", 1);
        ParkingLot parkingLotB = new ParkingLot("B", 2);
        ParkingLot parkingLotC = new ParkingLot("C", 3);
        String carPlateNumber = "京A12345";
        for (int i = 1; i < 101; i++) {
            parkingLotA.parking(new Car(carPlateNumber + i));
        }
        List<ParkingLot> parkingLots = Stream.of(parkingLotA, parkingLotB, parkingLotC)
                .sorted(Comparator.comparing(ParkingLot::getSerialNumber))
                .collect(Collectors.toList());
        Car car = new Car("京B12345");
        Boy boy = new Boy(parkingLots);

        //when
        Ticket ticket = boy.parking(car);

        //then
        assertEquals("京B12345", ticket.getCarPlateNumber());
        assertEquals("B", ticket.getParkingLotNo());
    }

    @Test
    void should_parking_in_B_and_get_ticket_when_parking_given_A_and_B_is_full_and_C_is_not_full() {
        //given
        ParkingLot parkingLotA = new ParkingLot("A", 1);
        ParkingLot parkingLotB = new ParkingLot("B", 2);
        ParkingLot parkingLotC = new ParkingLot("C", 3);
        String carPlateNumberA = "京A12345";
        String carPlateNumberB = "京B12345";
        for (int i = 1; i < 101; i++) {
            parkingLotA.parking(new Car(carPlateNumberA + i));
            parkingLotB.parking(new Car(carPlateNumberB + i));
        }
        List<ParkingLot> parkingLots = Stream.of(parkingLotA, parkingLotB, parkingLotC)
                .sorted(Comparator.comparing(ParkingLot::getSerialNumber))
                .collect(Collectors.toList());
        Car car = new Car("京C12345");
        Boy boy = new Boy(parkingLots);

        //when
        Ticket ticket = boy.parking(car);

        //then
        assertEquals("京C12345", ticket.getCarPlateNumber());
        assertEquals("C", ticket.getParkingLotNo());
    }

    @Test
    void should_parking_failed_when_parking_given_A_and_B_and_C_are_full() {
        //given
        ParkingLot parkingLotA = new ParkingLot("A", 1);
        ParkingLot parkingLotB = new ParkingLot("B", 2);
        ParkingLot parkingLotC = new ParkingLot("C", 3);
        String carPlateNumberA = "京A12345";
        String carPlateNumberB = "京B12345";
        String carPlateNumberC = "京C12345";
        for (int i = 1; i < 101; i++) {
            parkingLotA.parking(new Car(carPlateNumberA + i));
            parkingLotB.parking(new Car(carPlateNumberB + i));
            parkingLotC.parking(new Car(carPlateNumberC + i));
        }
        List<ParkingLot> parkingLots = Stream.of(parkingLotA, parkingLotB, parkingLotC)
                .sorted(Comparator.comparing(ParkingLot::getSerialNumber))
                .collect(Collectors.toList());
        Car car = new Car("京C12345");
        Boy boy = new Boy(parkingLots);

        //when
        ParkingException parkingException = assertThrows(ParkingException.class, () -> boy.parking(car));

        //then
        assertEquals("parking lot is full", parkingException.getMessage());
    }

    @Test
    void should_parking_failed_when_parking_given_A_is_not_full_and_car_plate_number_is_exist_in_B() {
        //given
        ParkingLot parkingLotA = new ParkingLot("A", 1);
        ParkingLot parkingLotB = new ParkingLot("B", 2);
        ParkingLot parkingLotC = new ParkingLot("C", 3);
        String carPlateNumber = "京A12345";
        Car car = new Car(carPlateNumber);
        parkingLotB.parking(car);
        List<ParkingLot> parkingLots = Stream.of(parkingLotA, parkingLotB, parkingLotC)
                .sorted(Comparator.comparing(ParkingLot::getSerialNumber))
                .collect(Collectors.toList());
        Boy boy = new Boy(parkingLots);

        //when
        ParkingException parkingException = assertThrows(ParkingException.class, () -> boy.parking(car));

        //then
        assertEquals("car plate number is duplicated", parkingException.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void should_parking_failed_when_parking_given_A_is_not_full_and_car_plate_number_is_empty(String carPlateNumber) {
        //given
        ParkingLot parkingLotA = new ParkingLot("A", 1);
        ParkingLot parkingLotB = new ParkingLot("B", 2);
        ParkingLot parkingLotC = new ParkingLot("C", 3);
        Car car = new Car(carPlateNumber);
        List<ParkingLot> parkingLots = Stream.of(parkingLotA, parkingLotB, parkingLotC)
                .sorted(Comparator.comparing(ParkingLot::getSerialNumber))
                .collect(Collectors.toList());
        Boy boy = new Boy(parkingLots);

        //when
        ParkingException parkingException = assertThrows(ParkingException.class, () -> boy.parking(car));

        //then
        assertEquals("vehicle has not car plate number", parkingException.getMessage());
    }

    @Test
    void should_leave_success_when_pick_up_car_given_valid_ticket() {
        //given
        ParkingLot parkingLotA = new ParkingLot("A", 1);
        ParkingLot parkingLotB = new ParkingLot("B", 2);
        ParkingLot parkingLotC = new ParkingLot("C", 3);
        String carPlateNumber = "京A12345";
        Car car = new Car(carPlateNumber);
        List<ParkingLot> parkingLots = Stream.of(parkingLotA, parkingLotB, parkingLotC)
                .sorted(Comparator.comparing(ParkingLot::getSerialNumber))
                .collect(Collectors.toList());
        Boy boy = new Boy(parkingLots);
        Ticket ticket = boy.parking(car);

        //when
        String parkCarPlateNumber = boy.pickUp(ticket);

        //then
        assertEquals(carPlateNumber, parkCarPlateNumber);
        assertEquals(0, parkingLotA.getCarList().size());
    }

    @Test
    void should_pick_up_failed_when_pick_up_car_given_invalid_ticket_with_wrong_parking_lot_no() {
        //given
        ParkingLot parkingLotA = new ParkingLot("A", 1);
        ParkingLot parkingLotB = new ParkingLot("B", 2);
        ParkingLot parkingLotC = new ParkingLot("C", 3);
        String carPlateNumber = "京A12345";
        List<ParkingLot> parkingLots = Stream.of(parkingLotA, parkingLotB, parkingLotC)
                .sorted(Comparator.comparing(ParkingLot::getSerialNumber))
                .collect(Collectors.toList());
        Boy boy = new Boy(parkingLots);
        Ticket ticket = new Ticket(carPlateNumber, "wrongParkingLotNo");

        //when
        PickUpException pickUpException = assertThrows(PickUpException.class, () -> boy.pickUp(ticket));

        //then
        assertEquals("parking lot is not found", pickUpException.getMessage());
    }

    @Test
    void should_pick_up_failed_when_pick_up_car_given_car_already_pick_up() {
        //given
        ParkingLot parkingLotA = new ParkingLot("A", 1);
        ParkingLot parkingLotB = new ParkingLot("B", 2);
        ParkingLot parkingLotC = new ParkingLot("C", 3);
        String carPlateNumber = "京A12345";
        List<ParkingLot> parkingLots = Stream.of(parkingLotA, parkingLotB, parkingLotC)
                .sorted(Comparator.comparing(ParkingLot::getSerialNumber))
                .collect(Collectors.toList());
        Boy boy = new Boy(parkingLots);
        Car car = new Car(carPlateNumber);
        Ticket ticket = boy.parking(car);
        boy.pickUp(ticket);

        //when
        PickUpException pickUpException = assertThrows(PickUpException.class, () -> boy.pickUp(ticket));

        //then
        assertEquals("vehicle is not found", pickUpException.getMessage());
    }
}
