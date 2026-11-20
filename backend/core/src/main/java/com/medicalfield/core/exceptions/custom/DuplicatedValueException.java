package com.medicalfield.core.exceptions.custom;

public class DuplicatedValueException extends RuntimeException{
    public DuplicatedValueException(String message) {
        super(message);
    }
}
