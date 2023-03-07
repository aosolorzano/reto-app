package com.pichicha.reto.app.api.model;

import com.pichicha.reto.app.api.utils.enums.EstadoEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "CLIENTES")
@EqualsAndHashCode(callSuper = true)
public class Cliente extends Persona {

    @Column(name = "CONTRASENIA", length = 120, nullable = false)
    @NotEmpty(message = "validacion.clientes.contrasenia.NotEmpty.mensaje")
    @Size(min = 8, max = 12, message = "validacion.clientes.contrasenia.Size.mensaje")
    private String contrasenia;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO", nullable = false)
    @NotNull(message = "validacion.clientes.estado.NotNull.mensaje")
    private EstadoEnum estado;

}


