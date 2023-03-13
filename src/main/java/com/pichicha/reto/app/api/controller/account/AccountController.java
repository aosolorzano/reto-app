package com.pichicha.reto.app.api.controller.account;

import com.pichicha.reto.app.api.dto.common.IdCriteriaDTO;
import com.pichicha.reto.app.api.dto.common.StatusCriteriaDTO;
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
    public Mono<Account> findById(@RequestBody @Valid IdCriteriaDTO idCriteriaDTO) {
        LOGGER.debug("findById() - START: {}", idCriteriaDTO);
        return this.accountService.findById(idCriteriaDTO.id());
    }

    @PutMapping(ControllerUtil.STATUS_PATH)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> updateStatus(@RequestBody @Valid StatusCriteriaDTO statusCriteriaDTO) {
        LOGGER.debug("updateStatus(): {}", statusCriteriaDTO);
        return this.accountService.updateStatus(statusCriteriaDTO);
    }

    @PostMapping(ControllerUtil.DELETE_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@RequestBody @Valid IdCriteriaDTO idCriteriaDTO) {
        LOGGER.debug("delete(): {}", idCriteriaDTO);
        return this.accountService.delete(idCriteriaDTO.id());
    }

}
