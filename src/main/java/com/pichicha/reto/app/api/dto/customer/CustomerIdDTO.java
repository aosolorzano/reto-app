package com.pichicha.reto.app.api.dto.customer;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CustomerIdDTO(
        @NotEmpty(message = "validation.personas.id.NotEmpty.message")
        @Size(min = 10, max = 10, message = "validation.personas.id.Size.message")
        String id) {
}
