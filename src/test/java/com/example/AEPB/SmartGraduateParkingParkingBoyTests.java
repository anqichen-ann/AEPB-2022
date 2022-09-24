package com.example.AEPB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SmartGraduateParkingParkingBoyTests {

    private SmartGraduateParkingBoy smartBoy;

    @BeforeEach
    void setUp() {
        ParkingLot parkingLotA = new ParkingLot("A", 1);
        ParkingLot parkingLotB = new ParkingLot("B", 2);
        ParkingLot parkingLotC = new ParkingLot("C", 3);
        List<ParkingLot> parkingLots = Stream.of(parkingLotA, parkingLotB, parkingLotC)
                .sorted(Comparator.comparing(ParkingLot::getSerialNumber))
                .collect(Collectors.toList());
        smartBoy = new SmartGraduateParkingBoy(parkingLots);
    }

    @Test
    void should_parking_in_A_and_get_ticket_when_parking_given_A_B_C_is_empty() {
        //given
        String carPlateNumber = "京A12345";
        Car car = new Car(carPlateNumber);

        //when
        Ticket ticket = smartBoy.parking(car);

        //then
        assertEquals(carPlateNumber, ticket.getCarPlateNumber());
        assertEquals("A", ticket.getParkingLotNo());
    }


}
