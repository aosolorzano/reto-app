package com.pichicha.reto.app.api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class ErrorDetailsDTO {
    public final ZonedDateTime errorDate;
    public final String requestedPath;
    public final String errorMessage;
    public final String errorCode;
}
