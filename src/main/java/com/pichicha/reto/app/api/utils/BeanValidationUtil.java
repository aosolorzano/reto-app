package com.pichicha.reto.app.api.utils;

import com.pichicha.reto.app.api.dto.transaction.TransactionCreationDTO;
import com.pichicha.reto.app.api.dto.transaction.TransactionDTO;
import com.pichicha.reto.app.api.exception.ValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;

public final class BeanValidationUtil {

    private BeanValidationUtil() {
        // Empty constructor.
    }

    public static void validate(TransactionCreationDTO transactionCreationDTO) {
        try (ValidatorFactory factory = buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<TransactionCreationDTO>> violations = validator.validate(transactionCreationDTO);
            if (!violations.isEmpty()) {
                violations.stream().findFirst().ifPresent(BeanValidationUtil::throwTransactionCreationDtoException);
            }
        }
    }

    public static void validate(TransactionDTO transactionDTO) {
        try (ValidatorFactory factory = buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<TransactionDTO>> violations = validator.validate(transactionDTO);
            if (!violations.isEmpty()) {
                violations.stream().findFirst().ifPresent(BeanValidationUtil::throwTransactionDtoException);
            }
        }
    }

    private static void throwTransactionCreationDtoException(ConstraintViolation<TransactionCreationDTO> violation) {
        throw new ValidationException(violation.getMessage());
    }

    private static void throwTransactionDtoException(ConstraintViolation<TransactionDTO> violation) {
        throw new ValidationException(violation.getMessage());
    }
}
