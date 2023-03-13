package com.pichicha.reto.app.api.utils;

import com.pichicha.reto.app.api.dto.customer.CustomerDTO;
import com.pichicha.reto.app.api.dto.transaction.TransactionCreationDTO;
import com.pichicha.reto.app.api.dto.transaction.TransactionDTO;
import com.pichicha.reto.app.api.model.Customer;
import com.pichicha.reto.app.api.model.Transaction;
import org.springframework.beans.BeanUtils;

public final class EntityUtil {

    private EntityUtil() {
        // Private constructor to hide the implicit public one.
    }

    public static Customer toCustomerEntity(CustomerDTO dto) {
        Customer user = new Customer();
        BeanUtils.copyProperties(dto, user);
        return user;
    }

    public static CustomerDTO toCustomerDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        BeanUtils.copyProperties(customer, dto);
        return dto;
    }

    public static TransactionDTO toTransactionDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        BeanUtils.copyProperties(transaction, dto);
        return dto;
    }

    public static TransactionCreationDTO toTransactionCreationDTO(TransactionDTO transactionDTO) {
        TransactionCreationDTO transactionCreationDTO = new TransactionCreationDTO();
        BeanUtils.copyProperties(transactionDTO, transactionCreationDTO);
        return transactionCreationDTO;
    }
}
