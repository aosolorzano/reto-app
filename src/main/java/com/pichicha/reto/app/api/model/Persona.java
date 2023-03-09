package com.pichicha.reto.app.api.model;

import com.pichicha.reto.app.api.utils.enums.EnumGenre;
import jakarta.persistence.*;
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
    private String id;

    @Column(name = "NOMBRE", length = 30, nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENERO", nullable = false)
    private EnumGenre genero;

    @Column(name = "EDAD", nullable = false)
    private int edad;

    @Column(name = "DIRECCION", length = 90, nullable = false)
    private String direccion;

    @Column(name = "TELEFONO", length = 15, nullable = false)
    private String telefono;
}
