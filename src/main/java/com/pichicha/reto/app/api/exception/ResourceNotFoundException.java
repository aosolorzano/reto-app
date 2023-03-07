package com.pichicha.reto.app.api.exception;

import com.pichicha.reto.app.api.utils.enums.NotFoundErrorEnum;

public class ResourceNotFoundException extends RetoAppException {

    public ResourceNotFoundException(NotFoundErrorEnum errorEnum, Object... args) {
        super(errorEnum.getCode(), errorEnum.getMessage(), args);
    }
}
