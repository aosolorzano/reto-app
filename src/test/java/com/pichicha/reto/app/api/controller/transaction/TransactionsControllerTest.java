package com.pichicha.reto.app.api.controller.transaction;

import com.pichicha.reto.app.api.common.AbstractContainerBase;
import com.pichicha.reto.app.api.dto.transaction.TransactionCriteriaDTO;
import com.pichicha.reto.app.api.dto.transaction.TransactionDTO;
import com.pichicha.reto.app.api.dto.transaction.TransactionOperationDTO;
import com.pichicha.reto.app.api.dto.transaction.TransactionResponseDTO;
import com.pichicha.reto.app.api.utils.ControllerUtil;
import com.pichicha.reto.app.api.utils.enums.EnumCrudOperation;
import com.pichicha.reto.app.api.utils.enums.EnumStatus;
import com.pichicha.reto.app.api.utils.enums.EnumTransactionType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransactionsControllerTest extends AbstractContainerBase {

    private static final Long ACCOUNT_NUMBER = 478758L;

    @Autowired
    private WebTestClient webTestClient;

    private static TransactionOperationDTO transactionOperationDTO;
    private static TransactionDTO transactionDTO;

    @BeforeAll
    public static void init() {
        transactionDTO = TransactionDTO.builder()
                .type(EnumTransactionType.RET)
                .accountNumber(ACCOUNT_NUMBER)
                .value(575.00)
                .build();
        transactionOperationDTO = TransactionOperationDTO.builder()
                .transaction(transactionDTO)
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("Create transaction")
    void givenTransactionData_whenCreate_thenReturnCreatedTransaction() {
        transactionOperationDTO.setOperation(EnumCrudOperation.CREATE);
        this.webTestClient
                .post()
                .uri(ControllerUtil.TRANSACTION_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(transactionOperationDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TransactionResponseDTO.class)
                .value(responseDTO -> {
                    assertThat(responseDTO.getDate()).isNotNull();
                    assertThat(responseDTO.getTransactions()).hasSize(1);
                    var transactionResponseDTO = responseDTO.getTransactions().get(0);
                    assertThat(transactionResponseDTO.getId()).isPositive();
                    assertThat(transactionResponseDTO.getAccountNumber()).isEqualTo(transactionDTO.getAccountNumber());
                    assertThat(transactionResponseDTO.getStatus()).isEqualTo(EnumStatus.ACT);
                    assertThat(transactionResponseDTO.getValue()).isEqualTo(transactionDTO.getValue());
                    BeanUtils.copyProperties(transactionResponseDTO, transactionDTO);
                });
    }

    @Test
    @Order(2)
    @DisplayName("Find by account number and date")
    void givenTransactionData_whenFindByAccountAndDate_thenReturnTransactionsList() {
        TransactionCriteriaDTO transactionCriteriaDTO = TransactionCriteriaDTO.builder()
                .accountNumber(transactionDTO.getAccountNumber())
                .fromDate(new Date())
                .build();
        this.webTestClient
                .post()
                .uri(ControllerUtil.TRANSACTIONS_PATH)
                .bodyValue(transactionCriteriaDTO)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(TransactionDTO.class)
                .value(customerList -> {
                    Assertions.assertThat(customerList).isNotEmpty();
                    Assertions.assertThat(customerList).hasSize(1);
                });
    }

    @Test
    @Order(2)
    @DisplayName("Find by account number and dates")
    void givenTransactionData_whenFindByAccountAndDates_thenReturnTransactionsList() {
        TransactionCriteriaDTO transactionCriteriaDTO = TransactionCriteriaDTO.builder()
                .accountNumber(transactionDTO.getAccountNumber())
                .fromDate(new Date())
                .toDate(new Date())
                .build();
        this.webTestClient
                .post()
                .uri(ControllerUtil.TRANSACTIONS_PATH)
                .bodyValue(transactionCriteriaDTO)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(TransactionDTO.class)
                .value(customerList -> {
                    Assertions.assertThat(customerList).isNotEmpty();
                    Assertions.assertThat(customerList).hasSize(1);
                });
    }
}
