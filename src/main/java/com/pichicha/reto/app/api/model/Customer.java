package com.pichicha.reto.app.api.model;

import com.pichicha.reto.app.api.utils.enums.EnumStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CLIENTES")
@EqualsAndHashCode(callSuper = true)
public class Customer extends Persona {

    @Column(name = "CLAVE", length = 30, nullable = false)
    private String clave;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO", nullable = false)
    private EnumStatus estado;
}


