package com.pichicha.reto.app.api.controller.transaction;

import com.pichicha.reto.app.api.common.AbstractContainerBase;
import com.pichicha.reto.app.api.dto.common.ErrorDetailsDTO;
import com.pichicha.reto.app.api.dto.transaction.TransactionDTO;
import com.pichicha.reto.app.api.dto.transaction.TransactionOperationDTO;
import com.pichicha.reto.app.api.utils.ControllerUtil;
import com.pichicha.reto.app.api.utils.enums.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransactionControllerErrorTest extends AbstractContainerBase {

    public static final Long NON_EXISTING_ID = 1000L;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private MessageSource messageSource;

    private static TransactionOperationDTO transactionOperationDTO;
    private static TransactionDTO transactionDTO;

    @BeforeAll
    public static void init() {
        transactionDTO = TransactionDTO.builder()
                .id(NON_EXISTING_ID)
                .build();
        transactionOperationDTO = TransactionOperationDTO.builder()
                .transaction(transactionDTO)
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("Find non existing transaction - Spanish")
    void givenNonExistingTransaction_whenFindById_thenReturnError404() {
        transactionOperationDTO.setOperation(EnumCrudOperation.READ);
        this.webTestClient
                .post()
                .uri(ControllerUtil.TRANSACTION_PATH)
                .bodyValue(transactionOperationDTO)
                .header(HttpHeaders.ACCEPT_LANGUAGE, EnumLanguageCode.ES.getCode())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumAppError.TRANSACTION_NOT_FOUND.getCode());
                    assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo(getMessageSourceMessage(EnumLanguageCode.ES));
                });
    }

    @Test
    @Order(2)
    @DisplayName("Find non existing transaction - English")
    void givenNonExistingTransaction_whenFindById_thenReturnError404InEnglish() {
        transactionOperationDTO.setOperation(EnumCrudOperation.READ);
        this.webTestClient
                .post()
                .uri(ControllerUtil.TRANSACTION_PATH)
                .bodyValue(transactionOperationDTO)
                .header(HttpHeaders.ACCEPT_LANGUAGE, EnumLanguageCode.EN.getCode())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumAppError.TRANSACTION_NOT_FOUND.getCode());
                    assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo(getMessageSourceMessage(EnumLanguageCode.EN));
                });
    }

    @Test
    @Order(3)
    @DisplayName("Validate transaction operation DTO")
    void givenNonTransactionData_whenOperate_thenReturnValidationError() {
        TransactionOperationDTO transactionOperationDTO = TransactionOperationDTO.builder().build();
        this.webTestClient
                .post()
                .uri(ControllerUtil.TRANSACTION_PATH)
                .bodyValue(transactionOperationDTO)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumValidationError.BEAN_FIELD_VALIDATION.getCode());
                    assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo("La transacción no puede ser nula.");
                });
    }

    @Test
    @Order(4)
    @DisplayName("Validate transaction operation Enum")
    void givenNonOperationData_whenOperate_thenReturnValidationError() {
        TransactionOperationDTO transactionOperation = TransactionOperationDTO.builder()
                .transaction(transactionDTO)
                .operation(null)
                .build();
        this.webTestClient
                .post()
                .uri(ControllerUtil.TRANSACTION_PATH)
                .bodyValue(transactionOperation)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumValidationError.BEAN_FIELD_VALIDATION.getCode());
                    assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo("La operación de la transacción no puede ser nula.");
                });
    }

    @Test
    @Order(5)
    @DisplayName("Validate transaction ID")
    void givenTransactionWithoutId_whenOperate_thenReturnValidationError() {
        TransactionDTO transaction = TransactionDTO.builder().build();
        TransactionOperationDTO transactionOperation = TransactionOperationDTO.builder()
                .transaction(transaction)
                .operation(EnumCrudOperation.READ)
                .build();
        this.webTestClient
                .post()
                .uri(ControllerUtil.TRANSACTION_PATH)
                .bodyValue(transactionOperation)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumValidationError.BEAN_FIELD_VALIDATION.getCode());
                    assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo("El ID no puede ser nulo.");
                });
    }

    @Test
    @Order(6)
    @DisplayName("Validate transaction account number")
    void givenTransactionWithoutAccount_whenOperate_thenReturnValidationError() {
        TransactionDTO transaction = TransactionDTO.builder()
                .accountNumber(null)
                .type(EnumTransactionType.RET)
                .value(100.0)
                .build();
        TransactionOperationDTO transactionOperation = TransactionOperationDTO.builder()
                .transaction(transaction)
                .operation(EnumCrudOperation.CREATE)
                .build();
        this.webTestClient
                .post()
                .uri(ControllerUtil.TRANSACTION_PATH)
                .bodyValue(transactionOperation)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumValidationError.BEAN_FIELD_VALIDATION.getCode());
                    assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo("El numero de cuenta no puede ser nulo.");
                });
    }

    @NotNull
    private String getMessageSourceMessage(EnumLanguageCode enumLanguageCode) {
        return this.messageSource.getMessage(EnumAppError.TRANSACTION_NOT_FOUND.getMessage(),
                new Long[]{NON_EXISTING_ID}, new Locale(enumLanguageCode.getCode()));
    }
}
