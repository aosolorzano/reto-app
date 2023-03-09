package com.pichicha.reto.app.api.model;

import com.pichicha.reto.app.api.utils.enums.EnumStatus;
import com.pichicha.reto.app.api.utils.enums.EnumTransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MOVIMIENTOS")
public class Transaction {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOVIMIENTOS_SEQ")
    @SequenceGenerator(name = "MOVIMIENTOS_SEQ", sequenceName = "MOVIMIENTOS_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "FECHA", nullable = false)
    private ZonedDateTime fecha;

    @Column(name = "NUMERO_CUENTA", nullable = false)
    @NotNull(message = "validacion.movimientos.cuenta.NotNull.mensaje")
    private Long numeroCuenta;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO", length = 3, nullable = false)
    @NotNull(message = "validacion.movimientos.tipo.NotNull.mensaje")
    private EnumTransactionType tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO", length = 3, nullable = false)
    private EnumStatus estado;

    @Column(name = "VALOR", nullable = false)
    @Min(value = 1, message = "validacion.movimientos.valor.Min.mensaje")
    private double valor;

    @Column(name = "SALDO_INICIAL", nullable = false)
    private double saldoInicial;

}
