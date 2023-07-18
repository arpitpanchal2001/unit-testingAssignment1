package com.simform.testing.exception;

public class InvalidId extends RuntimeException {
    public InvalidId(String message) {
        super(message);
    }
}
