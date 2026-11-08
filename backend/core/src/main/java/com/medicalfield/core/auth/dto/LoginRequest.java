package com.medicalfield.core.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

    @NotBlank
    @Schema(example = "CRM-UF xxxxxx")
    String identity,

    @NotBlank
    @Schema(example = "123456")
    String password

) {
}
