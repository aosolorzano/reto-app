package com.pichicha.reto.app.api.dto.transaction;

import com.pichicha.reto.app.api.utils.enums.EnumTransactionType;
import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;

public class TransactionCriteriaDTO {

    @NotNull(message = "validation.accounts.id.NotNull.message")
    private Long accountNumber;

    private ZonedDateTime fromDate;

    private ZonedDateTime toDate;

    private EnumTransactionType type;

}
