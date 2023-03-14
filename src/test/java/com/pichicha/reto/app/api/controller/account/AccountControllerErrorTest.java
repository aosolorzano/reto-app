package com.pichicha.reto.app.api.controller.account;

import com.pichicha.reto.app.api.common.AbstractContainerBase;
import com.pichicha.reto.app.api.dto.common.IdCriteriaDTO;
import com.pichicha.reto.app.api.dto.common.StatusCriteriaDTO;
import com.pichicha.reto.app.api.dto.common.ErrorDetailsDTO;
import com.pichicha.reto.app.api.model.Account;
import com.pichicha.reto.app.api.utils.ControllerUtil;
import com.pichicha.reto.app.api.utils.DataUtil;
import com.pichicha.reto.app.api.utils.enums.EnumNotFoundError;
import com.pichicha.reto.app.api.utils.enums.EnumLanguageCode;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerErrorTest extends AbstractContainerBase {

    public static final Long NON_EXISTING_ID = 890100L;
    public static final String CUSTOMER_ID = "0987654321";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private WebTestClient webTestClient;

    private static Account account;

    @BeforeAll
    public static void init() {
        account = DataUtil.getAccountTemplate();
        account.setNumeroCuenta(NON_EXISTING_ID);
        account.setClienteId(CUSTOMER_ID);
    }

    @Test
    @DisplayName("Find non existing account - Spanish")
    void givenNonExistingAccount_whenFindById_thenReturnError404() {
        this.webTestClient
                .post()
                .uri(ControllerUtil.ACCOUNT_PATH.concat(ControllerUtil.FIND_PATH))
                .bodyValue(new IdCriteriaDTO(account.getNumeroCuenta()))
                .header(HttpHeaders.ACCEPT_LANGUAGE, EnumLanguageCode.ES.getCode())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumNotFoundError.ACCOUNT_NOT_FOUND.getCode());
                    assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo(getMessageSourceMessage(EnumLanguageCode.ES));
                });
    }

    @Test
    @DisplayName("Find non existing account - English")
    void givenNonExistingAccount_whenFindById_thenReturnError404InEnglish() {
        this.webTestClient
                .post()
                .uri(ControllerUtil.ACCOUNT_PATH.concat(ControllerUtil.FIND_PATH))
                .bodyValue(new IdCriteriaDTO(account.getNumeroCuenta()))
                .header(HttpHeaders.ACCEPT_LANGUAGE, EnumLanguageCode.EN.getCode())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumNotFoundError.ACCOUNT_NOT_FOUND.getCode());
                    assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo(getMessageSourceMessage(EnumLanguageCode.EN));
                });
    }

    @Test
    @DisplayName("Update status for non existing account - Spanish")
    void givenNonExistingAccount_whenUpdateStatus_thenReturnError404() {
        StatusCriteriaDTO statusCriteriaDTO = StatusCriteriaDTO.builder()
                .id(account.getNumeroCuenta())
                .status(account.getEstado())
                .build();
        this.webTestClient
                .put()
                .uri(ControllerUtil.ACCOUNT_PATH.concat(ControllerUtil.STATUS_PATH))
                .header(HttpHeaders.ACCEPT_LANGUAGE, EnumLanguageCode.ES.getCode())
                .bodyValue(statusCriteriaDTO)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumNotFoundError.ACCOUNT_NOT_FOUND.getCode());
                    assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo(getMessageSourceMessage(EnumLanguageCode.ES));
                });
    }

    @Test
    @DisplayName("Update status for non existing account - English")
    void givenNonExistingAccount_whenUpdateStatus_thenReturnError404InEnglish() {
        StatusCriteriaDTO statusCriteriaDTO = StatusCriteriaDTO.builder()
                .id(account.getNumeroCuenta())
                .status(account.getEstado())
                .build();
        this.webTestClient
                .put()
                .uri(ControllerUtil.ACCOUNT_PATH.concat(ControllerUtil.STATUS_PATH))
                .header(HttpHeaders.ACCEPT_LANGUAGE, EnumLanguageCode.EN.getCode())
                .bodyValue(statusCriteriaDTO)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumNotFoundError.ACCOUNT_NOT_FOUND.getCode());
                    assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo(getMessageSourceMessage(EnumLanguageCode.EN));
                });
    }

    @Test
    @DisplayName("Delete non existing account - Spanish")
    void givenNonExistingAccount_whenDelete_thenReturnError404() {
        this.webTestClient
                .post()
                .uri(ControllerUtil.ACCOUNT_PATH.concat(ControllerUtil.DELETE_PATH))
                .bodyValue(new IdCriteriaDTO(account.getNumeroCuenta()))
                .header(HttpHeaders.ACCEPT_LANGUAGE, EnumLanguageCode.ES.getCode())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumNotFoundError.ACCOUNT_NOT_FOUND.getCode());
                    assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo(getMessageSourceMessage(EnumLanguageCode.ES));
                });
    }

    @Test
    @DisplayName("Delete non existing account - English")
    void givenNonExistingAccount_whenDelete_thenReturnError404InEnglish() {
        this.webTestClient
                .post()
                .uri(ControllerUtil.ACCOUNT_PATH.concat(ControllerUtil.DELETE_PATH))
                .bodyValue(new IdCriteriaDTO(account.getNumeroCuenta()))
                .header(HttpHeaders.ACCEPT_LANGUAGE, EnumLanguageCode.EN.getCode())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumNotFoundError.ACCOUNT_NOT_FOUND.getCode());
                    assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo(getMessageSourceMessage(EnumLanguageCode.EN));
                });
    }

    @NotNull
    private String getMessageSourceMessage(EnumLanguageCode enumLanguageCode) {
        return this.messageSource.getMessage(EnumNotFoundError.ACCOUNT_NOT_FOUND.getMessage(),
                new Long[]{account.getNumeroCuenta()}, new Locale(enumLanguageCode.getCode()));
    }
}
