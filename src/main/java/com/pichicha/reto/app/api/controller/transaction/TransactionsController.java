package com.pichicha.reto.app.api.controller.transaction;

import com.pichicha.reto.app.api.dto.transaction.TransactionCriteriaDTO;
import com.pichicha.reto.app.api.dto.transaction.TransactionDTO;
import com.pichicha.reto.app.api.service.TransactionService;
import com.pichicha.reto.app.api.utils.ControllerUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(ControllerUtil.TRANSACTIONS_PATH)
public class TransactionsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionsController.class);

    private final TransactionService transactionService;

    public TransactionsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping()
    public Flux<TransactionDTO> find(@RequestBody @Valid TransactionCriteriaDTO transactionCriteriaDTO) {
        LOGGER.debug("find() - START");
        return this.transactionService.find(transactionCriteriaDTO);
    }
}
