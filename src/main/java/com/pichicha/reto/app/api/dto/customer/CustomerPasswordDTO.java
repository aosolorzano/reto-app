package com.pichicha.reto.app.api.dto.customer;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerPasswordDTO {

    @NotEmpty(message = "validacion.personas.identificacion.NotEmpty.mensaje")
    @Size(min = 10, max = 10, message = "validacion.personas.identificacion.Size.mensaje")
    private String id;

    @NotEmpty(message = "validacion.clientes.password.NotEmpty.mensaje")
    @Size(min = 8, max = 12, message = "validacion.clientes.password.Size.mensaje")
    private String password;
}
