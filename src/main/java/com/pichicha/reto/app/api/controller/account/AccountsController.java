package com.pichicha.reto.app.api.controller.account;

import com.pichicha.reto.app.api.dto.account.AccountCriteriaDTO;
import com.pichicha.reto.app.api.model.Account;
import com.pichicha.reto.app.api.service.AccountService;
import com.pichicha.reto.app.api.utils.ControllerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(ControllerUtil.ACCOUNTS_PATH)
public class AccountsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsController.class);

    private final AccountService accountService;

    public AccountsController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public Flux<Account> find(@RequestBody AccountCriteriaDTO accountCriteriaDTO) {
        LOGGER.debug("find() - START");
        return Flux.empty();
    }
}
