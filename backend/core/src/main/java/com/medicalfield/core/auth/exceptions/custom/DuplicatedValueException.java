package com.medicalfield.core.auth.exceptions.custom;

public class DuplicatedValueException extends RuntimeException{
    public DuplicatedValueException(String message) {
        super(message);
    }
}
