package com.pichicha.reto.app.api.controller;

import com.pichicha.reto.app.api.model.Account;
import com.pichicha.reto.app.api.services.AccountService;
import com.pichicha.reto.app.api.utils.ControllerUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ControllerUtil.ACCOUNT_PATH)
public class AccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Account> crear(@RequestBody @Valid Account nuevoAccount) {
        LOGGER.debug("crear(): {}", nuevoAccount);
        return this.accountService.crear(nuevoAccount);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Account> actualizar(@PathVariable("id") Long id,
                                    @RequestBody @Valid Account accountActualizada) {
        LOGGER.debug("actualizar(): {}", accountActualizada);
        return this.accountService.actualizar(id, accountActualizada);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> eliminar(@PathVariable("id") Long id) {
        LOGGER.debug("eliminar(): {}", id);
        return this.accountService.eliminar(id);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Account> buscarPorId(@PathVariable("id") Long id) {
        LOGGER.debug("buscarPorId() - START: {}", id);
        return this.accountService.buscarPorId(id);
    }

}
