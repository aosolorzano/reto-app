package com.pichicha.reto.app.api.dto.transaction;

import com.pichicha.reto.app.api.utils.enums.EnumCrudOperation;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionOperationDTO {

    @NotNull(message = "transaction.operation.dto.object.NotNull.message")
    private TransactionDTO transaction;

    @NotNull(message = "transaction.operation.crud.enum.NotNull.message")
    private EnumCrudOperation operation;
}
