package com.pichicha.reto.app.api.dto.account;

import com.pichicha.reto.app.api.utils.enums.EnumAccountType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreationDTO {

    @NotEmpty(message = "validation.personas.id.NotEmpty.message")
    @Size(min = 10, max = 10, message = "validation.personas.id.Size.message")
    private String clienteId;

    @NotNull(message = "validation.type.NotNull.message")
    private EnumAccountType tipo;
}
