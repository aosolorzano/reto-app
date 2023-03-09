package com.pichicha.reto.app.api.services;

import com.pichicha.reto.app.api.exception.ResourceNotFoundException;
import com.pichicha.reto.app.api.model.Account;
import com.pichicha.reto.app.api.repository.AccountRepository;
import com.pichicha.reto.app.api.utils.enums.EnumAppError;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Mono<Account> buscarPorId(long id) {
        return Mono.fromSupplier(() -> this.accountRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(EnumAppError.ACCOUNT_NOT_FOUND, id)))
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Account> crear(Account account) {
        return Mono.just(account)
                .map(this.accountRepository::save);
    }

    public Mono<Account> actualizar(Long id, Account accountActualizada) {
        return this.buscarPorId(id)
                .doOnNext(cuentaGuardada -> accountActualizada.setSaldo(cuentaGuardada.getSaldo()))
                .doOnNext(cuentaGuardada -> BeanUtils.copyProperties(accountActualizada, cuentaGuardada))
                .map(this.accountRepository::save);
    }

    public Account actualizarSaldo(Long id, Double nuevoSaldo) {
        Account accountGuardada = this.accountRepository.findById(id).orElse(null);
        if (accountGuardada == null) {
            throw new ResourceNotFoundException(EnumAppError.ACCOUNT_NOT_FOUND, id);
        }
        accountGuardada.setSaldo(nuevoSaldo);
        return this.accountRepository.save(accountGuardada);
    }

    public Mono<Void> eliminar(long cuentaId) {
        return this.buscarPorId(cuentaId)
                .doOnNext(this.accountRepository::delete)
                .then();
    }

    public Flux<Account> buscarPorCliente(String clienteId) {
        return Flux.fromIterable(this.accountRepository.findByClienteId(clienteId))
                .subscribeOn(Schedulers.boundedElastic());
    }
}
