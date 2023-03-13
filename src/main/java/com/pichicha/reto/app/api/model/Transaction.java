package com.pichicha.reto.app.api.model;

import com.pichicha.reto.app.api.utils.enums.EnumStatus;
import com.pichicha.reto.app.api.utils.enums.EnumTransactionType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "MOVIMIENTOS")
public class Transaction {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOVIMIENTOS_SEQ")
    @SequenceGenerator(name = "MOVIMIENTOS_SEQ", sequenceName = "MOVIMIENTOS_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "FECHA", nullable = false)
    private ZonedDateTime date;

    @Column(name = "NUMERO_CUENTA", nullable = false)
    private Long accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO", length = 3, nullable = false)
    private EnumTransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO", length = 3, nullable = false)
    private EnumStatus status;

    @Column(name = "VALOR", nullable = false)
    private double value;

    @Column(name = "SALDO_INICIAL", nullable = false)
    private double initialBalance;
}
