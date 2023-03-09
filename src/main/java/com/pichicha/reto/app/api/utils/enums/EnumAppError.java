package com.pichicha.reto.app.api.utils.enums;

public enum EnumAppError {

    BEAN_FIELD_VALIDATION("RTO-FLD-001", null),
    PERSONA_NOT_FOUND("RTO-001", "persona.no.encontrada.mensaje"),
    CUSTOMER_NOT_FOUND("RTO-002", "cliente.no.encontrado.mensaje"),
    ACCOUNT_NOT_FOUND("RTO-003", "cuenta.no.encontrada.mensaje"),
    INSUFFICIENT_BALANCE("RTO-TRA-001", "movimiento.error.saldo.insuficiente.mensaje");

    private final String code;
    private final String message;

    EnumAppError(String code, String message) {
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
