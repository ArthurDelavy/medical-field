package com.medicalfield.core.medicine.dto;

import java.time.OffsetDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewMedicineRequest(

    @NotBlank @Size(min = 3, max = 100)
    @Schema(example = "Ibuprofeno")
    String name,

    @NotBlank @Size(min = 3, max = 100)
    @Schema(example = "Haleon")
    String manufacturer,

    @NotBlank @Size(min = 3, max = 100)
    @Schema(example = "HSU83G33847SBH")
    String batchNumber,

    @FutureOrPresent
    @Schema(example = "")
    OffsetDateTime expiresAt
) {
}
