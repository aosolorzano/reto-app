package com.pichicha.reto.app.api.dto.account;

import com.pichicha.reto.app.api.utils.enums.EnumAccountType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountCriteriaDTO {

    private Long accountNumber;

    private String customerId;

    private EnumAccountType type;
}
