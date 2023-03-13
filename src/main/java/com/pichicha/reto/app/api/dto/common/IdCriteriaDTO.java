package com.pichicha.reto.app.api.dto.common;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record IdCriteriaDTO(
        @Min(value = 1, message = "validation.id.Min.message")
        @NotNull(message = "validation.id.NotNull.message")
        Long id) {
}
