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

    @NotEmpty(message = "validation.personas.id.NotEmpty.message")
    @Size(min = 10, max = 10, message = "validation.personas.id.Size.message")
    private String id;

    @NotEmpty(message = "validation.personas.name.NotEmpty.message")
    @Size(min = 5, max = 30, message = "validation.personas.name.Size.message")
    private String nombre;

    @NotNull(message = "validation.personas.genre.NotNull.message")
    private EnumGenre genero;

    @Min(value = 1, message = "validation.personas.age.Min.message")
    private int edad;

    @NotEmpty(message = "validation.personas.address.NotEmpty.message")
    @Size(min = 10, max = 30, message = "validation.personas.address.Size.message")
    private String direccion;

    @NotEmpty(message = "validation.personas.phone.NotEmpty.message")
    @Size(min = 10, max = 10, message = "validation.personas.phone.Size.message")
    private String telefono;
}
