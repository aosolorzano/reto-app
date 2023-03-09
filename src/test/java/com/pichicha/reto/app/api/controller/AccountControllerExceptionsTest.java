package com.pichicha.reto.app.api.controller;

import com.pichicha.reto.app.api.common.AbstractContainerBase;
import com.pichicha.reto.app.api.dto.ErrorDetailsDTO;
import com.pichicha.reto.app.api.model.Account;
import com.pichicha.reto.app.api.utils.ControllerUtil;
import com.pichicha.reto.app.api.utils.DataUtil;
import com.pichicha.reto.app.api.utils.enums.EnumLanguageCode;
import com.pichicha.reto.app.api.utils.enums.EnumAppError;
import org.assertj.core.api.Assertions;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Locale;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@AutoConfigureWebTestClient
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerExceptionsTest extends AbstractContainerBase {

    public static final Long ID_NO_EXISTENTE = 890100L;
    public static final String CLIENT_ID = "0987654321";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private WebTestClient webTestClient;

    private static Account account;

    @BeforeAll
    public static void init() {
        account = DataUtil.getAccountTemplateDTO();
        account.setNumeroCuenta(ID_NO_EXISTENTE);
        account.setClienteId(CLIENT_ID);
    }

    @Test
    @DisplayName("Buscar account que no existe")
    void givenNotExistingAccount_whenFindById_thenReturnError404() {
        this.webTestClient
                .get()
                .uri(ControllerUtil.ACCOUNT_PATH.concat("/{id}"), account.getNumeroCuenta())
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_LANGUAGE, EnumLanguageCode.ES.getCode())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    Assertions.assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumAppError.ACCOUNT_NOT_FOUND.getCode());
                    Assertions.assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo(getMessageSourceMessage(EnumLanguageCode.ES));
                });
    }

    @Test
    @DisplayName("Buscar account que no existe - English")
    void givenNotExistingAccount_whenFindById_thenReturnError404InEnglish() {
        this.webTestClient
                .get()
                .uri(ControllerUtil.ACCOUNT_PATH.concat("/{id}"), account.getNumeroCuenta())
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_LANGUAGE, EnumLanguageCode.EN.getCode())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    Assertions.assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumAppError.ACCOUNT_NOT_FOUND.getCode());
                    Assertions.assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo(errorDetailsDTO.getErrorMessage())
                            .isEqualTo(getMessageSourceMessage(EnumLanguageCode.EN));
                });
    }

    @Test
    @DisplayName("Actualizar account que no existe")
    void givenNotExistingAccount_whenUpdate_thenReturnError404() {
        this.webTestClient
                .put()
                .uri(ControllerUtil.ACCOUNT_PATH.concat("/{id}"), account.getNumeroCuenta())
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_LANGUAGE, EnumLanguageCode.ES.getCode())
                .bodyValue(account)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    Assertions.assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumAppError.ACCOUNT_NOT_FOUND.getCode());
                    Assertions.assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo(getMessageSourceMessage(EnumLanguageCode.ES));
                });
    }

    @Test
    @DisplayName("Actualizar account que no existe - English")
    void givenNotExistingAccount_whenUpdate_thenReturnError404InEnglish() {
        this.webTestClient
                .put()
                .uri(ControllerUtil.ACCOUNT_PATH.concat("/{id}"), account.getNumeroCuenta())
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_LANGUAGE, EnumLanguageCode.EN.getCode())
                .bodyValue(account)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    Assertions.assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumAppError.ACCOUNT_NOT_FOUND.getCode());
                    Assertions.assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo(errorDetailsDTO.getErrorMessage())
                            .isEqualTo(getMessageSourceMessage(EnumLanguageCode.EN));
                });
    }

    @Test
    @DisplayName("Eliminar account que no existe")
    void givenNotExistingAccount_whenDelete_thenReturnError404() {
        this.webTestClient
                .delete()
                .uri(ControllerUtil.ACCOUNT_PATH.concat("/{id}"), account.getNumeroCuenta())
                .header(HttpHeaders.ACCEPT_LANGUAGE, EnumLanguageCode.ES.getCode())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    Assertions.assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumAppError.ACCOUNT_NOT_FOUND.getCode());
                    Assertions.assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo(errorDetailsDTO.getErrorMessage())
                            .isEqualTo(getMessageSourceMessage(EnumLanguageCode.ES));
                });
    }

    @Test
    @DisplayName("Eliminar account que no existe - English")
    void givenNotExistingAccount_whenDelete_thenReturnError404InEnglish() {
        this.webTestClient
                .delete()
                .uri(ControllerUtil.ACCOUNT_PATH.concat("/{id}"), account.getNumeroCuenta())
                .header(HttpHeaders.ACCEPT_LANGUAGE, EnumLanguageCode.EN.getCode())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    Assertions.assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumAppError.ACCOUNT_NOT_FOUND.getCode());
                    Assertions.assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo(errorDetailsDTO.getErrorMessage())
                            .isEqualTo(getMessageSourceMessage(EnumLanguageCode.EN));
                });
    }

    @NotNull
    private String getMessageSourceMessage(EnumLanguageCode enumLanguageCode) {
        return this.messageSource.getMessage(EnumAppError.ACCOUNT_NOT_FOUND.getMessage(),
                new Long[]{account.getNumeroCuenta()}, new Locale(enumLanguageCode.getCode()));
    }
}
