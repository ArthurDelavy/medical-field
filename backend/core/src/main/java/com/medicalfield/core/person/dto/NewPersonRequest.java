package com.medicalfield.core.person.dto;

import com.medicalfield.core.person.enums.Gender;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewPersonRequest(

    @NotBlank @Size(max = 100)
    @Schema(example = "Fulano")
    String name,

    @NotNull
    @Schema(example = "M")
    Gender gender,

    @Size(max = 100)
    @Schema(example = "39495820485")
    String document
) {
}
