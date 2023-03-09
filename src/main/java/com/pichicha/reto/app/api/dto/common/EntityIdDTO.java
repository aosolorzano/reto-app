package com.pichicha.reto.app.api.dto.common;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record EntityIdDTO(
        @Min(value = 1, message = "validacion.entidades.id.Min.mensaje")
        @NotNull(message = "validacion.entidades.id.NotNull.mensaje")
        Long id) {
}
