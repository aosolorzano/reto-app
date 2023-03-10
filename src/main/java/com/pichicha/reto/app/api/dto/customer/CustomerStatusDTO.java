package com.pichicha.reto.app.api.dto.customer;

import com.pichicha.reto.app.api.utils.enums.EnumStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerStatusDTO {

    @NotEmpty(message = "validation.personas.id.NotEmpty.message")
    @Size(min = 10, max = 10, message = "validation.personas.id.Size.message")
    private String id;

    @NotNull(message = "validation.status.NotNull.message")
    private EnumStatus status;
}
