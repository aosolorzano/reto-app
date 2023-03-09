package com.pichicha.reto.app.api.controller.transaction;

import com.pichicha.reto.app.api.model.Transaction;
import com.pichicha.reto.app.api.service.TransactionService;
import com.pichicha.reto.app.api.utils.ControllerUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ControllerUtil.TRANSACTION_PATH)
public class TransactionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Transaction> crear(@RequestBody @Valid Transaction nuevoTransaction) {
        LOGGER.debug("crear(): {}", nuevoTransaction);
        return this.transactionService.crear(nuevoTransaction);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> eliminar(@PathVariable("id") Long id) {
        LOGGER.debug("eliminar(): {}", id);
        return this.transactionService.eliminar(id);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Transaction> buscarPorId(@PathVariable("id") Long id) {
        LOGGER.debug("buscarPorId() - START: {}", id);
        return this.transactionService.buscarPorId(id);
    }
}
