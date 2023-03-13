package com.pichicha.reto.app.api.service;

import com.pichicha.reto.app.api.dto.transaction.TransactionCreationDTO;
import com.pichicha.reto.app.api.dto.transaction.TransactionDTO;
import com.pichicha.reto.app.api.exception.TransactionException;
import com.pichicha.reto.app.api.exception.ResourceNotFoundException;
import com.pichicha.reto.app.api.model.Account;
import com.pichicha.reto.app.api.model.Transaction;
import com.pichicha.reto.app.api.repository.TransactionRepository;
import com.pichicha.reto.app.api.utils.EntityUtil;
import com.pichicha.reto.app.api.utils.enums.EnumAppError;
import com.pichicha.reto.app.api.utils.enums.EnumStatus;
import com.pichicha.reto.app.api.utils.enums.EnumTransactionType;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.ZonedDateTime;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final AccountService accountService;

    public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    public Mono<TransactionDTO> create(TransactionCreationDTO newTransaction) {
        return this.accountService.findById(newTransaction.getAccountNumber())
                .doOnNext(account -> this.updateBalanceForCreation(account, newTransaction))
                .map(updatedAccount -> this.createTransaction(updatedAccount, newTransaction))
                .map(EntityUtil::toTransactionDTO);
    }

    public Mono<TransactionDTO> findById(TransactionDTO transactionDTO) {
        return this.findTransactionById(transactionDTO.getId())
                .map(EntityUtil::toTransactionDTO);
    }

    public Mono<TransactionDTO> update(TransactionDTO transactionDTO) {
        return this.delete(transactionDTO)
                .map(deletedTransaction -> EntityUtil.toTransactionCreationDTO(transactionDTO))
                .flatMap(this::create);

    }

    public Mono<TransactionDTO> delete(TransactionDTO transactionDTO) {
        return this.findTransactionById(transactionDTO.getId())
                .doOnNext(this::updateBalanceForDeletion)
                .doOnNext(this.transactionRepository::delete)
                .map(EntityUtil::toTransactionDTO);
    }

    private void updateBalanceForCreation(Account account, TransactionCreationDTO newTransaction) {
        double newBalance = account.getSaldo();
        if (newTransaction.getType().equals(EnumTransactionType.RET)) {
            newBalance -= newTransaction.getValue();
        } else if (newTransaction.getType().equals(EnumTransactionType.DEP)) {
            newBalance += newTransaction.getValue();
        }
        verifyBalance(account, newBalance);
        Account updatedAccount = this.accountService.updateBalance(account.getNumeroCuenta(), newBalance);
        updatedAccount.setSaldoAnterior(account.getSaldo());
    }

    private void updateBalanceForDeletion(Transaction transaction) {
        if (transaction.getStatus().equals(EnumStatus.INA)) {
            throw new TransactionException(EnumAppError.NOT_ACTIVE_TRANSACTION, transaction.getId());
        }
        Account account = this.accountService.findByTransaction(transaction);
        double actualBalance = account.getSaldo();
        if (transaction.getType().equals(EnumTransactionType.RET)) {
            actualBalance += transaction.getValue();
        } else if (transaction.getType().equals(EnumTransactionType.DEP)) {
            actualBalance -= transaction.getValue();
        }
        verifyBalance(account, actualBalance);
        this.accountService.updateBalance(transaction.getAccountNumber(), actualBalance);
    }

    private static void verifyBalance(Account account, double newBalance) {
        if (newBalance < 0) {
            throw new TransactionException(EnumAppError.INSUFFICIENT_BALANCE,
                    account.getNumeroCuenta());
        }
    }

    private Transaction createTransaction(Account account, TransactionCreationDTO newTransaction) {
        Transaction transaction = new Transaction();
        BeanUtils.copyProperties(newTransaction, transaction);
        transaction.setDate(ZonedDateTime.now());
        transaction.setStatus(EnumStatus.ACT);
        transaction.setInitialBalance(account.getSaldoAnterior());
        return this.transactionRepository.save(transaction);
    }

    public Mono<Transaction> findTransactionById(long id) {
        return Mono.fromSupplier(() -> this.transactionRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(EnumAppError.TRANSACTION_NOT_FOUND, id)))
                .subscribeOn(Schedulers.boundedElastic());
    }
}
