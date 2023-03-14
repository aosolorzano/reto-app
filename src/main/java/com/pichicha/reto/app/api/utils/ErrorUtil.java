package com.pichicha.reto.app.api.utils;

import com.pichicha.reto.app.api.dto.common.ErrorDetailsDTO;
import org.springframework.web.server.ServerWebExchange;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Locale;

public final class ErrorUtil {

    private ErrorUtil() {
        // Empty constructor.
    }

    public static ErrorDetailsDTO getErrorDetailsVO(ServerWebExchange exchange,
                                                    String errorMessage,
                                                    String errorCode,
                                                    String zoneId) {
        return ErrorDetailsDTO.builder()
                .errorDate(ZonedDateTime.now(ZoneId.of(zoneId)))
                .requestedPath(exchange.getRequest().getPath().toString())
                .errorMessage(errorMessage)
                .errorCode(errorCode)
                .build();
    }

    public static Locale getLocale(ServerWebExchange exchange) {
        List<Locale> localeList = exchange.getRequest().getHeaders().getAcceptLanguageAsLocales();
        if (localeList.isEmpty()) {
            return new Locale("es", "EC");
        }
        return localeList.get(0);
    }
}
