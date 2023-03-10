package com.pichicha.reto.app.api.utils.enums;

public enum EnumValidationError {

    BEAN_FIELD_VALIDATION("RTO-FLD");

    private final String code;

    EnumValidationError(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
