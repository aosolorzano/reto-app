package com.pichicha.reto.app.api.service;

import com.pichicha.reto.app.api.dao.AccountDAO;
import com.pichicha.reto.app.api.dto.account.AccountCriteriaDTO;
import com.pichicha.reto.app.api.dto.common.StatusCriteriaDTO;
import com.pichicha.reto.app.api.exception.ResourceNotFoundException;
import com.pichicha.reto.app.api.exception.ValidationException;
import com.pichicha.reto.app.api.model.Account;
import com.pichicha.reto.app.api.model.Transaction;
import com.pichicha.reto.app.api.repository.AccountRepository;
import com.pichicha.reto.app.api.utils.enums.EnumNotFoundError;
import com.pichicha.reto.app.api.utils.enums.EnumStatus;
import com.pichicha.reto.app.api.utils.enums.EnumValidationError;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Objects;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountDAO accountDAO;

    public AccountService(AccountRepository accountRepository, AccountDAO accountDAO) {
        this.accountRepository = accountRepository;
        this.accountDAO = accountDAO;
    }

    public Mono<Account> findById(long id) {
        return Mono.fromSupplier(() -> this.accountRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(EnumNotFoundError.ACCOUNT_NOT_FOUND, id)))
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Account> create(Account account) {
        return Mono.just(account)
                .map(this.accountRepository::save);
    }

    public Mono<Void> updateStatus(StatusCriteriaDTO statusCriteriaDTO) {
        return this.findById(statusCriteriaDTO.getId())
                .doOnNext(actualAccount -> actualAccount.setEstado(statusCriteriaDTO.getStatus()))
                .map(this.accountRepository::save)
                .then();
    }

    public Account updateBalance(Long accountId, Double newBalance) {
        Account actualAccount = this.accountRepository.findById(accountId).orElse(null);
        if (Objects.isNull(actualAccount)) {
            throw new ResourceNotFoundException(EnumNotFoundError.ACCOUNT_NOT_FOUND, accountId);
        } else if (newBalance < 0) {
            throw new ValidationException(EnumValidationError.INSUFFICIENT_BALANCE);
        } else if (!actualAccount.getEstado().equals(EnumStatus.ACT)) {
            throw new ValidationException(EnumValidationError.NOT_ACTIVE_ACCOUNT);
        }
        actualAccount.setSaldo(newBalance);
        return this.accountRepository.save(actualAccount);
    }

    public Mono<Void> delete(long accountId) {
        return this.findById(accountId)
                .doOnNext(this.accountRepository::delete)
                .then();
    }

    public Account findByTransaction(Transaction transaction) {
        Account account = this.accountRepository.findById(transaction.getAccountNumber()).orElse(null);
        if (account == null) {
            throw new ResourceNotFoundException(EnumNotFoundError.ACCOUNT_NOT_FOUND,
                    transaction.getAccountNumber());
        }
        return account;
    }

    public Flux<Account> find(AccountCriteriaDTO accountCriteriaDTO) {
        return Flux.fromStream(() -> this.accountDAO.find(accountCriteriaDTO).stream());
    }
}
