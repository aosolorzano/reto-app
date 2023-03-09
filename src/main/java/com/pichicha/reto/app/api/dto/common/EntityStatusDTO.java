package com.pichicha.reto.app.api.dto.common;

import com.pichicha.reto.app.api.utils.enums.EnumStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EntityStatusDTO {

    @Min(value = 1, message = "validacion.entidades.id.Min.mensaje")
    @NotNull(message = "validacion.entidades.id.NotNull.mensaje")
    private Long id;

    @NotNull(message = "validacion.entidades.estado.NotNull.mensaje")
    private EnumStatus status;
}
