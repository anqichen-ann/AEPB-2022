package com.example.AEPB.exception;

public class PickUpException extends RuntimeException{
    private static final long serialVersionUID = -6378259097216686489L;

    public PickUpException(String message) {
        super(message);
    }
}
