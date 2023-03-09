package com.pichicha.reto.app.api.utils;

import com.pichicha.reto.app.api.dto.customer.CustomerDTO;
import com.pichicha.reto.app.api.model.Customer;
import org.springframework.beans.BeanUtils;

public final class EntityUtil {

    private EntityUtil() {
        // Private constructor to hide the implicit public one.
    }

    public static Customer toEntity(CustomerDTO dto){
        Customer user = new Customer();
        BeanUtils.copyProperties(dto, user);
        return user;
    }

    public static CustomerDTO toDto(Customer user){
        CustomerDTO dto = new CustomerDTO();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }
}
