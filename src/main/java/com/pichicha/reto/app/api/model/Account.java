package com.pichicha.reto.app.api.model;

import com.pichicha.reto.app.api.utils.enums.EnumStatus;
import com.pichicha.reto.app.api.utils.enums.EnumAccountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotEmpty(message = "validacion.cuentas.clienteId.NotEmpty.mensaje")
    @Size(min = 10, max = 10, message = "validacion.cuentas.clienteId.Size.mensaje")
    private String clienteId;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO", length = 3, nullable = false)
    @NotNull(message = "validacion.cuentas.tipo.NotNull.mensaje")
    private EnumAccountType tipo;

    @Column(name = "SALDO", nullable = false)
    private double saldo;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO", length = 3, nullable = false)
    @NotNull(message = "validacion.cuentas.estado.NotNull.mensaje")
    private EnumStatus estado;
}
