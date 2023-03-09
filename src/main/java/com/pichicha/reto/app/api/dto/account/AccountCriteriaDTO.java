package com.pichicha.reto.app.api.dto.account;

import com.pichicha.reto.app.api.utils.enums.EnumAccountType;
import com.pichicha.reto.app.api.utils.enums.EnumStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountCriteriaDTO {

    private Long numeroCuenta;

    private String clienteId;

    private EnumAccountType tipo;

    private EnumStatus estado;
}
