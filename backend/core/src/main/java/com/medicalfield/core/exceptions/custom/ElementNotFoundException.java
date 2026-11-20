package com.medicalfield.core.exceptions.custom;

public class ElementNotFoundException extends RuntimeException {
    public ElementNotFoundException(String message) {
        super(message);
    }    
}