package com.pichicha.reto.app.api.utils.enums;

public enum EnumAppError {

    PERSONA_NOT_FOUND("RTO-001", "persona.not.found.message"),
    CUSTOMER_NOT_FOUND("RTO-002", "customer.not.found.message"),
    ACCOUNT_NOT_FOUND("RTO-003", "account.not.found.message"),
    NO_CRITERIA_FOUND("RTO-004", "criteria.params.not.found.message"),
    INSUFFICIENT_BALANCE("RTO-TRA-001", "transaction.insufficient.balance.message");

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
