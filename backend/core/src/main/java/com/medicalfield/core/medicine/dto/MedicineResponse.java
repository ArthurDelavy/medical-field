package com.medicalfield.core.medicine.dto;

import java.time.OffsetDateTime;

import com.medicalfield.core.medicine.Medicine;

import io.swagger.v3.oas.annotations.media.Schema;
public record MedicineResponse(

    @Schema(example = "1")
    int id,

    @Schema(example = "Ibuprofeno")
    String name,

    @Schema(example = "Haleon")
    String manufacturer,

    @Schema(example = "HSU83G33847SBH")
    String batchNumber,

    @Schema(example = "")
    OffsetDateTime expiresAt
) {

    public MedicineResponse(Medicine data) {
        this(
            data.getId(),
            data.getName(),
            data.getManufacturer(),
            data.getBatchNumber(),
            data.getExpiresAt()
        );
    }
}
