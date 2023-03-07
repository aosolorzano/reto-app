package com.pichicha.reto.app.api.exception;

import com.pichicha.reto.app.api.utils.enums.MovimientoErrorEnum;

public class MovimientoException extends RetoAppException {

    public MovimientoException(MovimientoErrorEnum errorEnum, Object... args) {
        super(errorEnum.getCode(), errorEnum.getMessage(), args);
    }
}
