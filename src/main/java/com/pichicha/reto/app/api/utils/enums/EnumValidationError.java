package com.pichicha.reto.app.api.utils.enums;

public enum EnumValidationError {

    BEAN_FIELD_VALIDATION("RTO-FLD", null),

    NOT_ACTIVE_ACCOUNT("RTO-TRA-001", "account.not.active.message"),
    NOT_ACTIVE_TRANSACTION("RTO-TRA-002", "transaction.not.active.message"),
    INSUFFICIENT_BALANCE("RTO-TRA-003", "transaction.insufficient.balance.message"),
    INVALID_DATE_RANGE("RTO-TRA-004", "transaction.insufficient.balance.message"),
    NO_CRITERIA_FOUND("RTO-TRA-005", "criteria.params.not.found.message");

    private final String code;
    private final String message;

    EnumValidationError(String code, String message) {
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
