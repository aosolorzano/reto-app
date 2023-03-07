package com.pichicha.reto.app.api.utils.enums;

public enum MovimientoErrorEnum {

    SALDO_INSUFICIENTE("MOV-001", "movimiento.error.saldo.insuficiente.mensaje");

    private final String code;
    private final String message;

    MovimientoErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
