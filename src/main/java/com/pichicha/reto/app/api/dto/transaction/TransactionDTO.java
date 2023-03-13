package com.pichicha.reto.app.api.dto.transaction;

import com.pichicha.reto.app.api.utils.enums.EnumStatus;
import com.pichicha.reto.app.api.utils.enums.EnumTransactionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    @Min(value = 1, message = "validation.id.Min.message")
    @NotNull(message = "validation.id.NotNull.message")
    private Long id;

    private ZonedDateTime date;

    private Long accountNumber;

    private EnumTransactionType type;

    private EnumStatus status;

    private double value;
}
