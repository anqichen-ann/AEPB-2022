package com.example.AEPB;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
