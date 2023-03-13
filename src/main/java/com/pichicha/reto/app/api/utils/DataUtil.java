package com.pichicha.reto.app.api.utils;

import com.pichicha.reto.app.api.dto.customer.CustomerDTO;
import com.pichicha.reto.app.api.model.Account;
import com.pichicha.reto.app.api.utils.enums.EnumAccountType;
import com.pichicha.reto.app.api.utils.enums.EnumGenre;
import com.pichicha.reto.app.api.utils.enums.EnumStatus;

public final class DataUtil {

    private DataUtil() {
        // Empty constructor.
    }

    public static CustomerDTO getCustomerTemplateDTO() {
        return CustomerDTO.builder()
                .nombre("Andres Solorzano")
                .genero(EnumGenre.M)
                .edad(37)
                .direccion("Street 123")
                .telefono("0987654321")
                .build();
    }

    public static Account getAccountTemplate() {
        return Account.builder()
                .tipo(EnumAccountType.CTE)
                .saldo(1000.00)
                .estado(EnumStatus.ACT)
                .build();
    }
}
