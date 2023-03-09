package com.pichicha.reto.app.api.dto.customer;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CustomerIdDTO(
        @NotEmpty(message = "validacion.personas.identificacion.NotEmpty.mensaje")
        @Size(min = 10, max = 10, message = "validacion.personas.identificacion.Size.mensaje")
        String id) {
}
