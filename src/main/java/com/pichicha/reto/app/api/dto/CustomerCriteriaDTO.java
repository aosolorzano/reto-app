package com.pichicha.reto.app.api.dto;

import com.pichicha.reto.app.api.utils.enums.EnumGenre;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerCriteriaDTO {

    @NotEmpty(message = "validacion.personas.identificacion.NotEmpty.mensaje")
    @Size(min = 10, max = 10, message = "validacion.personas.identificacion.Size.mensaje")
    private String id;

    private String nombre;

    private EnumGenre genero;

    private Integer edad;

    private String direccion;

    private String telefono;
}
