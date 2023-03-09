package com.pichicha.reto.app.api.service;

import com.pichicha.reto.app.api.dto.common.EntityStatusDTO;
import com.pichicha.reto.app.api.exception.ResourceNotFoundException;
import com.pichicha.reto.app.api.model.Account;
import com.pichicha.reto.app.api.repository.AccountRepository;
import com.pichicha.reto.app.api.utils.enums.EnumAppError;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Objects;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Mono<Account> findById(long id) {
        return Mono.fromSupplier(() -> this.accountRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(EnumAppError.ACCOUNT_NOT_FOUND, id)))
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Account> create(Account account) {
        return Mono.just(account)
                .map(this.accountRepository::save);
    }

    public Mono<Void> updateStatus(EntityStatusDTO entityStatusDTO) {
        return this.findById(entityStatusDTO.getId())
                .doOnNext(actualAccount -> actualAccount.setEstado(entityStatusDTO.getStatus()))
                .map(this.accountRepository::save)
                .then();
    }

    public Account updateBalance(Long accountId, Double newBalance) {
        Account actualAccount = this.accountRepository.findById(accountId).orElse(null);
        if (Objects.isNull(actualAccount)) {
            throw new ResourceNotFoundException(EnumAppError.ACCOUNT_NOT_FOUND, accountId);
        }
        actualAccount.setSaldo(newBalance);
        return this.accountRepository.save(actualAccount);
    }

    public Mono<Void> delete(long accountId) {
        return this.findById(accountId)
                .doOnNext(this.accountRepository::delete)
                .then();
    }

    public Flux<Account> findByCustomerId(String customerId) {
        return Flux.empty();
    }
}
