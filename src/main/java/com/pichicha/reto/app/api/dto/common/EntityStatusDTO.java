package com.pichicha.reto.app.api.dto.common;

import com.pichicha.reto.app.api.utils.enums.EnumStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EntityStatusDTO {

    @Min(value = 1, message = "validation.id.Min.message")
    @NotNull(message = "validation.id.NotNull.message")
    private Long id;

    @NotNull(message = "validacion.entidades.estado.NotNull.mensaje")
    private EnumStatus status;
}
