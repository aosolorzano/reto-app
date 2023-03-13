package com.pichicha.reto.app.api.exception;

import com.pichicha.reto.app.api.utils.enums.EnumValidationError;

public class ValidationException extends ApplicationException {

    public ValidationException(String messageCode, Object... args) {
        super(EnumValidationError.BEAN_FIELD_VALIDATION.getCode(), messageCode, args);
    }
}
