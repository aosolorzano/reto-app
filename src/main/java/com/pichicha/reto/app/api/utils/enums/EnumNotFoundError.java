package com.pichicha.reto.app.api.utils.enums;

public enum EnumNotFoundError {

    PERSONA_NOT_FOUND("RTO-001", "persona.not.found.message"),
    CUSTOMER_NOT_FOUND("RTO-002", "customer.not.found.message"),
    ACCOUNT_NOT_FOUND("RTO-003", "account.not.found.message"),
    TRANSACTION_NOT_FOUND("RTO-004", "transaction.not.found.message");

    private final String code;
    private final String message;

    EnumNotFoundError(String code, String message) {
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
