package com.erlin.foodtracker.exception;

public class MealAlreadyExistException extends RuntimeException {
    public MealAlreadyExistException(String message) {
        super(message);
    }
}
