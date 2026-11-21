package com.medicalfield.core.records.dto;

import java.time.Instant;

import com.medicalfield.core.person.dto.PersonResponse;
import com.medicalfield.core.records.MedicalRecord;
import com.medicalfield.core.records.enums.BloodType;

public record MedicalRecordResponse(

    int id,

    PersonResponse person,

    BloodType bloodType,

    String allergies,

    String comorbidities,

    String continuousUseMedicines,

    String generalObservations,

    Instant createdAt

) {

    public MedicalRecordResponse(MedicalRecord data) {
        this(
            data.getId(),
            new PersonResponse(data.getPerson()),
            data.getBloodType(),
            data.getAllergies(),
            data.getComorbidities(),
            data.getContinuousUseMedicines(),
            data.getGeneralObservations(),
            data.getCreatedAt()
        );
    }
}
