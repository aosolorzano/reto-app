package com.pichicha.reto.app.api.exception;

import com.pichicha.reto.app.api.utils.enums.EnumAppError;

public class SearchException extends ApplicationException {

    public SearchException(EnumAppError errorEnum, Object... args) {
        super(errorEnum.getCode(), errorEnum.getMessage(), args);
    }
}
