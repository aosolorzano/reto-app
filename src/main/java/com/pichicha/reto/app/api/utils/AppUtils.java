package com.pichicha.reto.app.api.utils;

import com.pichicha.reto.app.api.model.Cliente;
import com.pichicha.reto.app.api.model.Cuenta;
import com.pichicha.reto.app.api.model.Movimiento;
import com.pichicha.reto.app.api.utils.enums.EstadoEnum;
import com.pichicha.reto.app.api.utils.enums.GeneroEnum;
import com.pichicha.reto.app.api.utils.enums.TipoCuentaEnum;
import com.pichicha.reto.app.api.utils.enums.TipoMovimientoEnum;

public final class AppUtils {

    public static final String CLIENTES_PATH = "/api/clientes";
    public static final String CUENTAS_PATH = "/api/cuentas";
    public static final String MOVIMIENTOS_PATH = "/api/movimientos";

    private AppUtils() {
        // Empty constructor.
    }

    public static Cliente getClienteTemplateObject() {
        return Cliente.builder()
                .nombre("Andres Solorzano")
                .genero(GeneroEnum.M)
                .edad(37)
                .direccion("Calle 1234")
                .telefono("0987654321")
                .contrasenia("andres123")
                .estado(EstadoEnum.ACT)
                .build();
    }

    public static Cuenta getCuentaTemplateObject() {
        return Cuenta.builder()
                .tipo(TipoCuentaEnum.CTE)
                .saldo(1000.00)
                .estado(EstadoEnum.ACT)
                .build();
    }

    public static Movimiento getMovimientoTemplateObject() {
        return Movimiento.builder()
                .numeroCuenta(478758L)
                .tipo(TipoMovimientoEnum.RET)
                .valor(575.00)
                .build();
    }
}
