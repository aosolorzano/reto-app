package com.pichicha.reto.app.api.controller.transaction;

import com.pichicha.reto.app.api.common.AbstractContainerBase;
import com.pichicha.reto.app.api.dto.common.IdCriteriaDTO;
import com.pichicha.reto.app.api.dto.transaction.TransactionDTO;
import com.pichicha.reto.app.api.dto.transaction.TransactionOperationDTO;
import com.pichicha.reto.app.api.dto.transaction.TransactionResponseDTO;
import com.pichicha.reto.app.api.model.Account;
import com.pichicha.reto.app.api.utils.ControllerUtil;
import com.pichicha.reto.app.api.utils.enums.EnumCrudOperation;
import com.pichicha.reto.app.api.utils.enums.EnumStatus;
import com.pichicha.reto.app.api.utils.enums.EnumTransactionType;
import org.junit.jupiter.api.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransactionControllerTest extends AbstractContainerBase {

    public static final long ACCOUNT_NUMBER = 478758L;

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
    @DisplayName("Validate created transaction")
    void givenAccountData_whenFindByCreatedTransaction_thenReturnAccountObject() {
        IdCriteriaDTO idCriteriaDTO = new IdCriteriaDTO(ACCOUNT_NUMBER);
        this.webTestClient
                .post()
                .uri(ControllerUtil.ACCOUNT_PATH.concat(ControllerUtil.FIND_PATH))
                .bodyValue(idCriteriaDTO)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Account.class)
                .value(account -> {
                    assertThat(account.getNumeroCuenta()).isEqualTo(ACCOUNT_NUMBER);
                    assertThat(account.getEstado()).isEqualTo(EnumStatus.ACT);
                    assertThat(account.getSaldo()).isEqualTo(1425.00);
                });
    }

    @Test
    @Order(3)
    @DisplayName("Update transaction")
    void givenTransactionData_whenUpdate_thenReturnUpdatedTransaction() {
        transactionOperationDTO.setOperation(EnumCrudOperation.UPDATE);
        transactionOperationDTO.getTransaction().setValue(1000.00);
        this.webTestClient
                .post()
                .uri(ControllerUtil.TRANSACTION_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(transactionOperationDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TransactionResponseDTO.class)
                .value(responseDTO -> {
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
    @Order(4)
    @DisplayName("Validate updated transaction")
    void givenAccountData_whenFindByUpdatedTransaction_thenReturnAccountObject() {
        IdCriteriaDTO idCriteriaDTO = new IdCriteriaDTO(ACCOUNT_NUMBER);
        this.webTestClient
                .post()
                .uri(ControllerUtil.ACCOUNT_PATH.concat(ControllerUtil.FIND_PATH))
                .bodyValue(idCriteriaDTO)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Account.class)
                .value(account -> {
                    assertThat(account.getNumeroCuenta()).isEqualTo(ACCOUNT_NUMBER);
                    assertThat(account.getEstado()).isEqualTo(EnumStatus.ACT);
                    assertThat(account.getSaldo()).isEqualTo(1000.00);
                });
    }

    @Test
    @Order(5)
    @DisplayName("Delete transaction")
    void givenTransactionData_whenDelete_thenReturnDeletedTransaction() {
        transactionOperationDTO.setOperation(EnumCrudOperation.DELETE);
        this.webTestClient
                .post()
                .uri(ControllerUtil.TRANSACTION_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(transactionOperationDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TransactionResponseDTO.class)
                .value(responseDTO -> {
                    assertThat(responseDTO.getTransactions()).hasSize(1);
                    var transactionResponseDTO = responseDTO.getTransactions().get(0);
                    assertThat(transactionResponseDTO.getAccountNumber()).isEqualTo(transactionDTO.getAccountNumber());
                    assertThat(transactionResponseDTO.getStatus()).isEqualTo(EnumStatus.ACT);
                    assertThat(transactionResponseDTO.getValue()).isEqualTo(transactionDTO.getValue());
                });
    }

    @Test
    @Order(6)
    @DisplayName("Validate deleted transaction")
    void givenAccountData_whenFindByDeletedTransaction_thenReturnAccountObject() {
        IdCriteriaDTO idCriteriaDTO = new IdCriteriaDTO(ACCOUNT_NUMBER);
        this.webTestClient
                .post()
                .uri(ControllerUtil.ACCOUNT_PATH.concat(ControllerUtil.FIND_PATH))
                .bodyValue(idCriteriaDTO)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Account.class)
                .value(account -> {
                    assertThat(account.getNumeroCuenta()).isEqualTo(ACCOUNT_NUMBER);
                    assertThat(account.getEstado()).isEqualTo(EnumStatus.ACT);
                    assertThat(account.getSaldo()).isEqualTo(2000.00);
                });
    }
}
