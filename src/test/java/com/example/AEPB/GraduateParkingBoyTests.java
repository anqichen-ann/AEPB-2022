package com.example.AEPB;

import com.example.AEPB.exception.ParkingException;
import com.example.AEPB.exception.PickUpException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GraduateParkingBoyTests {

    private GraduateParkingBoy graduateParkingBoy;
    private ParkingLot parkingLotA;
    private ParkingLot parkingLotB;
    private ParkingLot parkingLotC;
    private static final String CAR_PLATE_NUMBER = "京A12345";

    @BeforeEach
    void setUp() {
        parkingLotA = new ParkingLot(100, "A", 1);
        parkingLotB = new ParkingLot(100, "B", 2);
        parkingLotC = new ParkingLot(100, "C", 3);
        List<ParkingLot> parkingLots = Stream.of(parkingLotA, parkingLotB, parkingLotC)
                .sorted(Comparator.comparing(ParkingLot::getSerialNumber))
                .collect(Collectors.toList());
        graduateParkingBoy = new GraduateParkingBoy(parkingLots);
    }

    @Test
    void should_parking_in_A_and_get_ticket_when_parking_given_A_is_not_full() {
        //given
        Car car = new Car(CAR_PLATE_NUMBER);

        //when
        Ticket ticket = graduateParkingBoy.parking(car);

        //then
        assertEquals(CAR_PLATE_NUMBER, ticket.getCarPlateNumber());
        assertEquals("A", ticket.getParkingLotNo());
    }

    @Test
    void should_parking_in_A_and_get_ticket_when_parking_given_A_is_full_and_then_pick_up_one_car() {
        //given
        for (int i = 1; i < 101; i++) {
            parkingLotA.parking(new Car(CAR_PLATE_NUMBER + i));
        }
        Car car = new Car(CAR_PLATE_NUMBER);
        graduateParkingBoy.pickUp(new Ticket("京A123451", "A"));

        //when
        Ticket ticket = graduateParkingBoy.parking(car);

        //then
        assertEquals(CAR_PLATE_NUMBER, ticket.getCarPlateNumber());
        assertEquals("A", ticket.getParkingLotNo());
        assertEquals(100, parkingLotA.getCarList().size());
    }

    @Test
    void should_parking_in_B_and_get_ticket_when_parking_given_A_is_full_and_B_is_not_full() {
        //given
        for (int i = 1; i < 101; i++) {
            parkingLotA.parking(new Car(CAR_PLATE_NUMBER + i));
        }
        Car car = new Car("京B12345");

        //when
        Ticket ticket = graduateParkingBoy.parking(car);

        //then
        assertEquals("京B12345", ticket.getCarPlateNumber());
        assertEquals("B", ticket.getParkingLotNo());
    }

    @Test
    void should_parking_in_B_and_get_ticket_when_parking_given_A_and_B_is_full_and_C_is_not_full() {
        //given
        String carPlateNumberA = "京A12345";
        String carPlateNumberB = "京B12345";
        for (int i = 1; i < 101; i++) {
            parkingLotA.parking(new Car(carPlateNumberA + i));
            parkingLotB.parking(new Car(carPlateNumberB + i));
        }
        Car car = new Car("京C12345");

        //when
        Ticket ticket = graduateParkingBoy.parking(car);

        //then
        assertEquals("京C12345", ticket.getCarPlateNumber());
        assertEquals("C", ticket.getParkingLotNo());
    }

    @Test
    void should_parking_failed_when_parking_given_A_and_B_and_C_are_full() {
        //given
        String carPlateNumberA = "京A12345";
        String carPlateNumberB = "京B12345";
        String carPlateNumberC = "京C12345";
        for (int i = 1; i < 101; i++) {
            parkingLotA.parking(new Car(carPlateNumberA + i));
            parkingLotB.parking(new Car(carPlateNumberB + i));
            parkingLotC.parking(new Car(carPlateNumberC + i));
        }
        Car car = new Car("京C12345");

        //when
        ParkingException parkingException = assertThrows(ParkingException.class, () -> graduateParkingBoy.parking(car));

        //then
        assertEquals("parking lot is full", parkingException.getMessage());
    }

    @Test
    void should_parking_failed_when_parking_given_A_is_not_full_and_car_plate_number_is_exist_in_B() {
        //given
        Car car = new Car(CAR_PLATE_NUMBER);
        parkingLotB.parking(car);

        //when
        ParkingException parkingException = assertThrows(ParkingException.class, () -> graduateParkingBoy.parking(car));

        //then
        assertEquals("car plate number is duplicated", parkingException.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void should_parking_failed_when_parking_given_A_is_not_full_and_car_plate_number_is_empty(String carPlateNumber) {
        //given
        Car car = new Car(carPlateNumber);

        //when
        ParkingException parkingException = assertThrows(ParkingException.class, () -> graduateParkingBoy.parking(car));

        //then
        assertEquals("vehicle has not car plate number", parkingException.getMessage());
    }

    @Test
    void should_leave_success_when_pick_up_car_given_valid_ticket() {
        //given
        Car car = new Car(CAR_PLATE_NUMBER);
        Ticket ticket = graduateParkingBoy.parking(car);

        //when
        String parkCarPlateNumber = graduateParkingBoy.pickUp(ticket);

        //then
        assertEquals(CAR_PLATE_NUMBER, parkCarPlateNumber);
        assertEquals(0, parkingLotA.getCarList().size());
    }

    @Test
    void should_pick_up_failed_when_pick_up_car_given_invalid_ticket_with_wrong_parking_lot_no() {
        //given
        Ticket ticket = new Ticket(CAR_PLATE_NUMBER, "wrongParkingLotNo");

        //when
        PickUpException pickUpException = assertThrows(PickUpException.class, () -> graduateParkingBoy.pickUp(ticket));

        //then
        assertEquals("parking lot is not found", pickUpException.getMessage());
    }

    @Test
    void should_pick_up_failed_when_pick_up_car_given_car_already_pick_up() {
        //given
        Car car = new Car(CAR_PLATE_NUMBER);
        Ticket ticket = graduateParkingBoy.parking(car);
        graduateParkingBoy.pickUp(ticket);

        //when
        PickUpException pickUpException = assertThrows(PickUpException.class, () -> graduateParkingBoy.pickUp(ticket));

        //then
        assertEquals("vehicle is not found", pickUpException.getMessage());
    }
}
