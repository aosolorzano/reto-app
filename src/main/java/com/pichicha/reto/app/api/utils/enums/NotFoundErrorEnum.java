package com.pichicha.reto.app.api.utils.enums;

public enum NotFoundErrorEnum {

    PERSONA_NO_ENCONTRADA("RTO-001", "persona.no.encontrada.mensaje"),
    CLIENTE_NO_ENCONTRADO("RTO-002", "cliente.no.encontrado.mensaje"),
    CUENTA_NO_ENCONTRADA("RTO-003", "cuenta.no.encontrada.mensaje");

    private final String code;
    private final String message;

    NotFoundErrorEnum(String code, String message) {
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
