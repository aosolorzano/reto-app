package com.pichicha.reto.app.api.controller.account;

import com.pichicha.reto.app.api.dto.common.EntityIdDTO;
import com.pichicha.reto.app.api.dto.common.EntityStatusDTO;
import com.pichicha.reto.app.api.model.Account;
import com.pichicha.reto.app.api.service.AccountService;
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
    public Mono<Account> create(@RequestBody @Valid Account account) {
        LOGGER.debug("create(): {}", account);
        return this.accountService.create(account);
    }

    @PostMapping(ControllerUtil.FIND_PATH)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Account> findById(@RequestBody @Valid EntityIdDTO entityIdDTO) {
        LOGGER.debug("findById() - START: {}", entityIdDTO);
        return this.accountService.findById(entityIdDTO.id());
    }

    @PutMapping(ControllerUtil.STATUS_PATH)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> updateStatus(@RequestBody @Valid EntityStatusDTO entityStatusDTO) {
        LOGGER.debug("updateStatus(): {}", entityStatusDTO);
        return this.accountService.updateStatus(entityStatusDTO);
    }

    @PostMapping(ControllerUtil.DELETE_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@RequestBody @Valid EntityIdDTO entityIdDTO) {
        LOGGER.debug("delete(): {}", entityIdDTO);
        return this.accountService.delete(entityIdDTO.id());
    }

}
