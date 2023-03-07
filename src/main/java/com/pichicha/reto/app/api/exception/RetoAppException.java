package com.pichicha.reto.app.api.exception;

public class RetoAppException extends RuntimeException {

    private final String errorCode;
    private final String errorMessageKey;
    private final transient Object[] args;

    protected RetoAppException(String errorCode, String errorMessageKey, Object... args) {
        super();
        this.errorCode = errorCode;
        this.errorMessageKey = errorMessageKey;
        this.args = args;
    }

    protected String getErrorCode() {
        return errorCode;
    }

    protected String getErrorMessageKey() {
        return errorMessageKey;
    }

    protected Object[] getArgs() {
        return args;
    }
}
