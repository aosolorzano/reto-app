package com.pichicha.reto.app.api.controller.transaction;

import com.pichicha.reto.app.api.common.AbstractContainerBase;
import com.pichicha.reto.app.api.dto.common.ErrorDetailsDTO;
import com.pichicha.reto.app.api.dto.transaction.TransactionCriteriaDTO;
import com.pichicha.reto.app.api.utils.ControllerUtil;
import com.pichicha.reto.app.api.utils.enums.EnumLanguageCode;
import com.pichicha.reto.app.api.utils.enums.EnumValidationError;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
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
class TransactionsControllerErrorTest extends AbstractContainerBase {

    public static final long ACCOUNT_NUMBER = 478758L;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    @DisplayName("Find transactions without account ID - Spanish")
    void givenTransactionsData_whenFindWithoutAccount_thenReturnValidationError() {
        TransactionCriteriaDTO transactionCriteriaDTO = TransactionCriteriaDTO.builder()
                .fromDate(new Date())
                .build();
        this.webTestClient
                .post()
                .uri(ControllerUtil.TRANSACTIONS_PATH)
                .bodyValue(transactionCriteriaDTO)
                .header(HttpHeaders.ACCEPT_LANGUAGE, EnumLanguageCode.ES.getCode())
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumValidationError.BEAN_FIELD_VALIDATION.getCode());
                    assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo("El numero de cuenta no puede ser nulo.");
                });
    }

    @Test
    @Order(2)
    @DisplayName("Find transactions without account ID - English")
    void givenTransactionsData_whenFindWithoutAccount_thenReturnValidationErrorEnglish() {
        TransactionCriteriaDTO transactionCriteriaDTO = TransactionCriteriaDTO.builder()
                .fromDate(new Date())
                .build();
        this.webTestClient
                .post()
                .uri(ControllerUtil.TRANSACTIONS_PATH)
                .bodyValue(transactionCriteriaDTO)
                .header(HttpHeaders.ACCEPT_LANGUAGE, EnumLanguageCode.EN.getCode())
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumValidationError.BEAN_FIELD_VALIDATION.getCode());
                    assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo("Account number cannot be null.");
                });
    }

    @Test
    @Order(3)
    @DisplayName("Find transactions without 'from date' - Spanish")
    void givenTransactionsData_whenFindWithoutDate_thenReturnValidationError() {
        TransactionCriteriaDTO transactionCriteriaDTO = TransactionCriteriaDTO.builder()
                .accountNumber(ACCOUNT_NUMBER)
                .build();
        this.webTestClient
                .post()
                .uri(ControllerUtil.TRANSACTIONS_PATH)
                .bodyValue(transactionCriteriaDTO)
                .header(HttpHeaders.ACCEPT_LANGUAGE, EnumLanguageCode.ES.getCode())
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumValidationError.BEAN_FIELD_VALIDATION.getCode());
                    assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo("La 'fecha desde' no puede ser nula.");
                });
    }

    @Test
    @Order(4)
    @DisplayName("Find transactions without 'from date' - English")
    void givenTransactionsData_whenFindWithoutDate_thenReturnValidationErrorEnglish() {
        TransactionCriteriaDTO transactionCriteriaDTO = TransactionCriteriaDTO.builder()
                .accountNumber(ACCOUNT_NUMBER)
                .build();
        this.webTestClient
                .post()
                .uri(ControllerUtil.TRANSACTIONS_PATH)
                .bodyValue(transactionCriteriaDTO)
                .header(HttpHeaders.ACCEPT_LANGUAGE, EnumLanguageCode.EN.getCode())
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumValidationError.BEAN_FIELD_VALIDATION.getCode());
                    assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo("The 'from date' cannot be null.");
                });
    }
}
