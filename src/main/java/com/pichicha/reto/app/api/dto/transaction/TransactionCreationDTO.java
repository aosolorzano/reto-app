package com.pichicha.reto.app.api.dto.transaction;

import com.pichicha.reto.app.api.utils.enums.EnumTransactionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransactionCreationDTO {

    @NotNull(message = "validation.account.id.NotNull.message")
    private Long accountNumber;

    @NotNull(message = "validation.type.NotNull.message")
    private EnumTransactionType type;

    @Min(value = 1, message = "validation.value.Min.message")
    private double value;
}
