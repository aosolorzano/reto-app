package com.pichicha.reto.app.api.utils.enums;

public enum EnumLanguageCode {

    EN("en"),
    ES("es");

    private final String code;

    EnumLanguageCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
