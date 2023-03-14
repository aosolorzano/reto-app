package com.pichicha.reto.app.api.exception;

import com.pichicha.reto.app.api.utils.enums.EnumNotFoundError;

public class ResourceNotFoundException extends ApplicationException {

    public ResourceNotFoundException(EnumNotFoundError errorEnum, Object... args) {
        super(errorEnum.getCode(), errorEnum.getMessage(), args);
    }
}
