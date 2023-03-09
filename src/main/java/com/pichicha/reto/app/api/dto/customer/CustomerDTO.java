package com.pichicha.reto.app.api.dto.customer;

import com.pichicha.reto.app.api.utils.enums.EnumGenre;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    @NotEmpty(message = "validacion.personas.identificacion.NotEmpty.mensaje")
    @Size(min = 10, max = 10, message = "validacion.personas.identificacion.Size.mensaje")
    private String id;

    @NotEmpty(message = "validacion.personas.nombre.NotEmpty.mensaje")
    @Size(min = 5, max = 30, message = "validacion.personas.nombre.Size.mensaje")
    private String nombre;

    @NotNull(message = "validacion.personas.genero.NotNull.mensaje")
    private EnumGenre genero;

    @Min(value = 1, message = "validacion.personas.edad.Min.mensaje")
    private int edad;

    @NotEmpty(message = "validacion.personas.direccion.NotEmpty.mensaje")
    @Size(min = 10, max = 30, message = "validacion.personas.direccion.Size.mensaje")
    private String direccion;

    @NotEmpty(message = "validacion.personas.telefono.NotEmpty.mensaje")
    @Size(min = 10, max = 10, message = "validacion.personas.telefono.Size.mensaje")
    private String telefono;
}
