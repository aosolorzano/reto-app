package com.pichicha.reto.app.api.controller.transaction;

import com.pichicha.reto.app.api.dto.transaction.TransactionCreationDTO;
import com.pichicha.reto.app.api.dto.transaction.TransactionDTO;
import com.pichicha.reto.app.api.dto.transaction.TransactionOperationDTO;
import com.pichicha.reto.app.api.dto.transaction.TransactionResponseDTO;
import com.pichicha.reto.app.api.service.TransactionService;
import com.pichicha.reto.app.api.utils.BeanValidationUtil;
import com.pichicha.reto.app.api.utils.ControllerUtil;
import com.pichicha.reto.app.api.utils.EntityUtil;
import jakarta.validation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping(ControllerUtil.TRANSACTION_PATH)
public class TransactionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

    private final TransactionService transactionService;

    @Value("${reto.time.zone.id:-05:00}")
    private String zoneId;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<TransactionResponseDTO> operate(@RequestBody @Valid TransactionOperationDTO transactionOperation) {
        LOGGER.debug("operate(): {}", transactionOperation);
        return (switch (transactionOperation.getOperation()) {
            case CREATE -> this.transactionService.create(this.getCreationDTO(transactionOperation));
            case READ -> this.transactionService.findById(this.getTransactionDTO(transactionOperation));
            case UPDATE -> this.transactionService.update(this.getTransactionDTO(transactionOperation));
            case DELETE -> this.transactionService.delete(this.getTransactionDTO(transactionOperation));
        }).map(this::getResponseDTO);
    }

    private TransactionCreationDTO getCreationDTO(TransactionOperationDTO transactionOperation) {
        TransactionCreationDTO transactionCreationDTO = EntityUtil.toTransactionCreationDTO(transactionOperation
                .getTransaction());
        BeanValidationUtil.validate(transactionCreationDTO);
        return transactionCreationDTO;
    }

    private TransactionDTO getTransactionDTO(TransactionOperationDTO transactionOperation) {
        TransactionDTO transactionDTO = transactionOperation.getTransaction();
        BeanValidationUtil.validate(transactionDTO);
        return transactionOperation.getTransaction();
    }

    private TransactionResponseDTO getResponseDTO(TransactionDTO transaction) {
        return TransactionResponseDTO.builder()
                .date(ZonedDateTime.now(ZoneId.of(zoneId)))
                .transactions(List.of(transaction))
                .build();
    }
}
