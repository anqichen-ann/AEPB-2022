package com.example.AEPB.exception;

public class ParkingException extends RuntimeException{
    private static final long serialVersionUID = -6378259097216686452L;

    public ParkingException(String message) {
        super(message);
    }
}
