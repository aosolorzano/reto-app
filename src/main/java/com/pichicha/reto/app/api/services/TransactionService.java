package com.pichicha.reto.app.api.services;

import com.pichicha.reto.app.api.exception.TransactionException;
import com.pichicha.reto.app.api.exception.ResourceNotFoundException;
import com.pichicha.reto.app.api.model.Account;
import com.pichicha.reto.app.api.model.Transaction;
import com.pichicha.reto.app.api.repository.AccountRepository;
import com.pichicha.reto.app.api.repository.TransactionRepository;
import com.pichicha.reto.app.api.utils.enums.EnumAppError;
import com.pichicha.reto.app.api.utils.enums.EnumStatus;
import com.pichicha.reto.app.api.utils.enums.EnumTransactionType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.ZonedDateTime;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;

    private final AccountService accountService;

    public TransactionService(TransactionRepository transactionRepository, AccountService accountService,
                              AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    public Mono<Transaction> buscarPorId(long id) {
        return Mono.fromSupplier(() -> this.transactionRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(EnumAppError.ACCOUNT_NOT_FOUND, id)))
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Transaction> crear(Transaction nuevoTransaction) {
        return this.accountService.buscarPorId(nuevoTransaction.getNumeroCuenta())
                .publishOn(Schedulers.boundedElastic())
                .map(cuenta -> {
                    double saldoCuenta = cuenta.getSaldo();
                    nuevoTransaction.setSaldoInicial(saldoCuenta);
                    if (nuevoTransaction.getTipo().equals(EnumTransactionType.RET)) {
                        saldoCuenta -= nuevoTransaction.getValor();
                    } else if (nuevoTransaction.getTipo().equals(EnumTransactionType.DEP)) {
                        saldoCuenta += nuevoTransaction.getValor();
                    }
                    this.validarSaldoFinal(nuevoTransaction, saldoCuenta);
                    return this.accountService.actualizarSaldo(cuenta.getNumeroCuenta(), saldoCuenta);
                })
                .publishOn(Schedulers.boundedElastic())
                .map(cuenta -> {
                    nuevoTransaction.setFecha(ZonedDateTime.now());
                    nuevoTransaction.setEstado(EnumStatus.ACT);
                    return this.transactionRepository.save(nuevoTransaction);
                });
    }

    public Mono<Void> eliminar(Long id) {
        return this.buscarPorId(id)
                .publishOn(Schedulers.boundedElastic())
                .map(movimiento -> {
                    this.transactionRepository.delete(movimiento);
                    return movimiento;
                })
                .doOnNext(movimientoExistente -> {
                    Account account = this.validarCuenta(movimientoExistente);
                    double saldoCuenta = account.getSaldo();
                    if (movimientoExistente.getTipo().equals(EnumTransactionType.RET)) {
                        saldoCuenta += movimientoExistente.getValor();
                    } else if (movimientoExistente.getTipo().equals(EnumTransactionType.DEP)) {
                        saldoCuenta -= movimientoExistente.getValor();
                    }
                    this.validarSaldoFinal(movimientoExistente, saldoCuenta);
                    this.accountService.actualizarSaldo(movimientoExistente.getNumeroCuenta(),
                            saldoCuenta);
                })
                .then();
    }

    private void validarSaldoFinal(Transaction transactionExistente, double saldoCuenta) {
        if (saldoCuenta < 0) {
            transactionExistente.setFecha(ZonedDateTime.now());
            transactionExistente.setEstado(EnumStatus.INA);
            this.transactionRepository.save(transactionExistente);
            throw new TransactionException(EnumAppError.INSUFFICIENT_BALANCE,
                    transactionExistente.getNumeroCuenta());
        }
    }

    private Account validarCuenta(Transaction transactionExistente) {
        Account account = this.accountRepository.findById(transactionExistente.getNumeroCuenta()).orElse(null);
        if (account == null) {
            throw new ResourceNotFoundException(EnumAppError.ACCOUNT_NOT_FOUND,
                    transactionExistente.getNumeroCuenta());
        }
        return account;
    }
}
