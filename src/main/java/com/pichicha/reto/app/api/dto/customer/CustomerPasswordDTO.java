package com.pichicha.reto.app.api.dto.customer;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerPasswordDTO {

    @NotEmpty(message = "validation.personas.id.NotEmpty.message")
    @Size(min = 10, max = 10, message = "validation.personas.id.Size.message")
    private String id;

    @NotEmpty(message = "validation.customers.password.NotEmpty.message")
    @Size(min = 8, max = 12, message = "validation.customers.password.Size.message")
    private String password;
}
