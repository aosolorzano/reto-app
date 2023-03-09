package com.pichicha.reto.app.api.exception;

import com.pichicha.reto.app.api.utils.enums.EnumAppError;

public class ResourceNotFoundException extends ApplicationException {

    public ResourceNotFoundException(EnumAppError errorEnum, Object... args) {
        super(errorEnum.getCode(), errorEnum.getMessage(), args);
    }
}
