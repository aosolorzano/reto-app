package com.pichicha.reto.app.api.model;

import com.pichicha.reto.app.api.utils.enums.EnumAccountType;
import com.pichicha.reto.app.api.utils.enums.EnumStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CUENTAS")
public class Account {

    @Id
    @Column(name = "NUMERO_CUENTA", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUENTAS_SEQ")
    @SequenceGenerator(name = "CUENTAS_SEQ", sequenceName = "CUENTAS_SEQ", allocationSize = 1)
    private Long numeroCuenta;

    @Column(name = "CLIENTE_ID", length = 10, nullable = false)
    private String clienteId;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO", length = 3, nullable = false)
    private EnumAccountType tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO", length = 3, nullable = false)
    private EnumStatus estado;

    @Column(name = "SALDO", nullable = false)
    private double saldo;

    @Transient
    private double saldoAnterior;
}
