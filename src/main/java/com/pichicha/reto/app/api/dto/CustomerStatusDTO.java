package com.pichicha.reto.app.api.dto;

import com.pichicha.reto.app.api.utils.enums.EnumStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerStatusDTO {

    @NotEmpty(message = "validacion.personas.identificacion.NotEmpty.mensaje")
    @Size(min = 10, max = 10, message = "validacion.personas.identificacion.Size.mensaje")
    private String id;

    @NotNull(message = "validacion.clientes.estado.NotNull.mensaje")
    private EnumStatus status;
}
