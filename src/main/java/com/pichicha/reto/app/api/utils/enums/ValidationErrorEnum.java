package com.pichicha.reto.app.api.utils.enums;

public enum ValidationErrorEnum {

    FIELD_VALIDATION_ERROR("RTO-FLD-001");

    private final String code;

    ValidationErrorEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
