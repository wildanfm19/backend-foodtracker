package com.erlin.foodtracker.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
