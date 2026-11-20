package com.medicalfield.core.person.dto;

import com.medicalfield.core.person.Person;

public record PersonResponse(

    int id,

    String name,

    String gender,

    String document

) {

    public PersonResponse(Person data) {
        this(
            data.getId(),
            data.getName(),
            data.getGender().toString(),
            data.getDocument()
        );
    }
}
