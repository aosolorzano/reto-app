package com.pichicha.reto.app.api.controller.transaction;

import com.pichicha.reto.app.api.model.Transaction;
import com.pichicha.reto.app.api.service.TransactionService;
import com.pichicha.reto.app.api.utils.ControllerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public Flux<Transaction> findAll() {
        LOGGER.debug("findAll() - START");
        return Flux.empty();
    }
}
