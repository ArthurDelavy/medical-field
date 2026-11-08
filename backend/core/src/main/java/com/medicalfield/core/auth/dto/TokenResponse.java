package com.medicalfield.core.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record TokenResponse(

    @Schema(example = "u3d374hdj...")
    String token

) {
}
