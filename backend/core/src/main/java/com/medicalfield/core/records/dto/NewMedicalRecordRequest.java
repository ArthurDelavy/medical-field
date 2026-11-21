package com.medicalfield.core.records.dto;

import java.time.Instant;
import java.util.UUID;

import com.medicalfield.core.records.enums.BloodType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewMedicalRecordRequest(

    @Schema(example = "1")
    int personId,

    @Schema(example = "A+")
    BloodType bloodType,

    @Size(max = 5000)
    @Schema(example = "Dipirona")
    String allergies,

    @Size(max = 5000)
    @Schema(example = "Diabetes")
    String comorbidities,

    @Size(max = 5000)
    @Schema(example = "Metformina")
    String continuousUseMedicines,

    @Size(max = 5000)
    @Schema(example = "...")
    String generalObservations,

    Instant createdAt,

    @NotNull 
    UUID syncId
) {
}
