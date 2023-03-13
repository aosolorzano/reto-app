package com.pichicha.reto.app.api.service;

import com.pichicha.reto.app.api.common.AbstractContainerBase;
import com.pichicha.reto.app.api.dto.transaction.TransactionCreationDTO;
import com.pichicha.reto.app.api.dto.transaction.TransactionDTO;
import com.pichicha.reto.app.api.model.Account;
import com.pichicha.reto.app.api.utils.enums.EnumStatus;
import com.pichicha.reto.app.api.utils.enums.EnumTransactionType;
import org.junit.jupiter.api.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransactionServiceTest extends AbstractContainerBase {

    public static final long ACCOUNT_NUMBER = 478758L;

    @Autowired
    TransactionService transactionService;

    @Autowired
    AccountService accountService;

    private static TransactionDTO transactionDTO;

    @BeforeAll
    public static void init() {
        transactionDTO = TransactionDTO.builder()
                .type(EnumTransactionType.RET)
                .accountNumber(ACCOUNT_NUMBER)
                .value(575.00)
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("Create transaction")
    void givenTransactionData_whenCreate_thenReturnSavedTransaction() {
        var transactionCreationDTO = new TransactionCreationDTO();
        BeanUtils.copyProperties(transactionDTO, transactionCreationDTO);
        Mono<TransactionDTO> newTransaction = this.transactionService.create(transactionCreationDTO);
        StepVerifier.create(newTransaction)
                .assertNext(newTransactionDTO -> {
                    assertThat(newTransactionDTO.getId()).isPositive();
                    assertThat(newTransactionDTO.getAccountNumber()).isEqualTo(transactionCreationDTO.getAccountNumber());
                    assertThat(newTransactionDTO.getStatus()).isEqualTo(EnumStatus.ACT);
                    assertThat(newTransactionDTO.getValue()).isEqualTo(transactionCreationDTO.getValue());
                    BeanUtils.copyProperties(newTransactionDTO, transactionDTO);
                })
                .verifyComplete();
    }

    @Test
    @Order(2)
    @DisplayName("Validate created transaction")
    void givenAccountData_whenFindByCreatedTransaction_thenReturnAccountObject() {
        Mono<Account> savedAccount = this.accountService.findById(transactionDTO.getAccountNumber());
        StepVerifier.create(savedAccount)
                .assertNext(account -> {
                    assertThat(account.getNumeroCuenta()).isEqualTo(transactionDTO.getAccountNumber());
                    assertThat(account.getEstado()).isEqualTo(EnumStatus.ACT);
                    assertThat(account.getSaldo()).isEqualTo(1425.00);
                })
                .verifyComplete();
    }

    @Test
    @Order(3)
    @DisplayName("Update transaction")
    void givenTransactionData_whenUpdate_thenReturnUpdatedTransaction() {
        transactionDTO.setValue(1000.0);
        Mono<TransactionDTO> updatedTransaction = this.transactionService.update(transactionDTO);
        StepVerifier.create(updatedTransaction)
                .assertNext(updatedTransactionDTO -> {
                    assertThat(updatedTransactionDTO.getAccountNumber()).isEqualTo(transactionDTO.getAccountNumber());
                    assertThat(updatedTransactionDTO.getStatus()).isEqualTo(EnumStatus.ACT);
                    assertThat(updatedTransactionDTO.getValue()).isEqualTo(transactionDTO.getValue());
                    BeanUtils.copyProperties(updatedTransactionDTO, transactionDTO);
                })
                .verifyComplete();
    }

    @Test
    @Order(4)
    @DisplayName("Validate updated transaction")
    void givenAccountData_whenFindByUpdatedTransaction_thenReturnAccountObject() {
        Mono<Account> savedAccount = this.accountService.findById(transactionDTO.getAccountNumber());
        StepVerifier.create(savedAccount)
                .assertNext(account -> {
                    assertThat(account.getNumeroCuenta()).isEqualTo(transactionDTO.getAccountNumber());
                    assertThat(account.getEstado()).isEqualTo(EnumStatus.ACT);
                    assertThat(account.getSaldo()).isEqualTo(1000.00);
                })
                .verifyComplete();
    }

    @Test
    @Order(5)
    @DisplayName("Delete transaction")
    void givenTransactionData_whenDelete_thenReturnSavedTransaction() {
        Mono<TransactionDTO> deletedTransaction = this.transactionService.delete(transactionDTO);
        StepVerifier.create(deletedTransaction)
                .assertNext(deletedTransactionDTO -> {
                    assertThat(deletedTransactionDTO.getAccountNumber()).isEqualTo(transactionDTO.getAccountNumber());
                    assertThat(deletedTransactionDTO.getStatus()).isEqualTo(EnumStatus.ACT);
                    assertThat(deletedTransactionDTO.getValue()).isEqualTo(transactionDTO.getValue());
                })
                .verifyComplete();
    }

    @Test
    @Order(6)
    @DisplayName("Validate deleted transaction")
    void givenAccountData_whenFindByDeletedTransaction_thenReturnAccountObject() {
        Mono<Account> savedAccount = this.accountService.findById(transactionDTO.getAccountNumber());
        StepVerifier.create(savedAccount)
                .assertNext(account -> {
                    assertThat(account.getNumeroCuenta()).isEqualTo(transactionDTO.getAccountNumber());
                    assertThat(account.getEstado()).isEqualTo(EnumStatus.ACT);
                    assertThat(account.getSaldo()).isEqualTo(2000.00);
                })
                .verifyComplete();
    }
}
