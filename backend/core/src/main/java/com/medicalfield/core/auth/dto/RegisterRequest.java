package com.medicalfield.core.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

    @NotBlank @Size(min = 3, max = 30)
    @Schema(example = "CRM-UF xxxxxx")
    String identity,

    @NotBlank @Size(min = 3, max = 100)
    @Schema(example = "Fulano de Tal")
    String name,

    @Email @Size(min = 3, max = 150)
    @Schema(example = "email@dominio.com")
    String email,

    @NotBlank @Size(min = 8, max = 500)
    @Schema(example = "123456")
    String password
) {
}
