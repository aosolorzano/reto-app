package com.pichicha.reto.app.api.model;

import com.pichicha.reto.app.api.utils.enums.GeneroEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PERSONAS")
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona {

    @Id
    @Column(name = "IDENTIFICACION", length = 10, nullable = false)
    @NotEmpty(message = "validacion.personas.identificacion.NotEmpty.mensaje")
    @Size(min = 10, max = 10, message = "validacion.personas.identificacion.Size.mensaje")
    private String id;

    @Column(name = "NOMBRE", length = 30, nullable = false)
    @NotEmpty(message = "validacion.personas.nombre.NotEmpty.mensaje")
    @Size(min = 5, max = 30, message = "validacion.personas.nombre.Size.mensaje")
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENERO", nullable = false)
    @NotNull(message = "validacion.personas.genero.NotNull.mensaje")
    private GeneroEnum genero;

    @Column(name = "EDAD", nullable = false)
    @Min(value = 1, message = "validacion.personas.edad.Min.mensaje")
    private int edad;

    @Column(name = "DIRECCION", length = 90, nullable = false)
    @NotEmpty(message = "validacion.personas.direccion.NotEmpty.mensaje")
    @Size(min = 10, max = 30, message = "validacion.personas.direccion.Size.mensaje")
    private String direccion;

    @Column(name = "TELEFONO", length = 15, nullable = false)
    @NotEmpty(message = "validacion.personas.telefono.NotEmpty.mensaje")
    @Size(min = 10, max = 10, message = "validacion.personas.telefono.Size.mensaje")
    private String telefono;
}
