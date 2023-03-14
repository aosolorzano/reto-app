package com.pichicha.reto.app.api.dto.transaction;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TransactionCriteriaDTO {

    @NotNull(message = "validation.account.id.NotNull.message")
    private Long accountNumber;

    @NotNull(message = "criteria.fromDate.NotNull.message")
    private Date fromDate;

    private Date toDate;

}
