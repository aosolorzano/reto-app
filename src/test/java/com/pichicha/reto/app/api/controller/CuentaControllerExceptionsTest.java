package com.pichicha.reto.app.api.controller;

import com.pichicha.reto.app.api.common.AbstractContainerBase;
import com.pichicha.reto.app.api.dto.ErrorDetailsDTO;
import com.pichicha.reto.app.api.model.Cuenta;
import com.pichicha.reto.app.api.utils.AppUtils;
import com.pichicha.reto.app.api.utils.enums.LanguageCodeEnum;
import com.pichicha.reto.app.api.utils.enums.NotFoundErrorEnum;
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
class CuentaControllerExceptionsTest extends AbstractContainerBase {

    public static final Long ID_NO_EXISTENTE = 890100L;
    public static final String CLIENT_ID = "0987654321";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private WebTestClient webTestClient;

    private static Cuenta cuenta;

    @BeforeAll
    public static void init() {
        cuenta = AppUtils.getCuentaTemplateObject();
        cuenta.setNumeroCuenta(ID_NO_EXISTENTE);
        cuenta.setClienteId(CLIENT_ID);
    }

    @Test
    @DisplayName("Buscar cuenta que no existe")
    void givenNotExistingAccount_whenFindById_thenReturnError404() {
        this.webTestClient
                .get()
                .uri(AppUtils.CUENTAS_PATH.concat("/{id}"), cuenta.getNumeroCuenta())
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_LANGUAGE, LanguageCodeEnum.ES.getCode())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    Assertions.assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(NotFoundErrorEnum.CUENTA_NO_ENCONTRADA.getCode());
                    Assertions.assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo(getMessageSourceMessage(LanguageCodeEnum.ES));
                });
    }

    @Test
    @DisplayName("Buscar cuenta que no existe - English")
    void givenNotExistingAccount_whenFindById_thenReturnError404InEnglish() {
        this.webTestClient
                .get()
                .uri(AppUtils.CUENTAS_PATH.concat("/{id}"), cuenta.getNumeroCuenta())
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_LANGUAGE, LanguageCodeEnum.EN.getCode())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    Assertions.assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(NotFoundErrorEnum.CUENTA_NO_ENCONTRADA.getCode());
                    Assertions.assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo(errorDetailsDTO.getErrorMessage())
                            .isEqualTo(getMessageSourceMessage(LanguageCodeEnum.EN));
                });
    }

    @Test
    @DisplayName("Actualizar cuenta que no existe")
    void givenNotExistingAccount_whenUpdate_thenReturnError404() {
        this.webTestClient
                .put()
                .uri(AppUtils.CUENTAS_PATH.concat("/{id}"), cuenta.getNumeroCuenta())
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_LANGUAGE, LanguageCodeEnum.ES.getCode())
                .bodyValue(cuenta)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    Assertions.assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(NotFoundErrorEnum.CUENTA_NO_ENCONTRADA.getCode());
                    Assertions.assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo(getMessageSourceMessage(LanguageCodeEnum.ES));
                });
    }

    @Test
    @DisplayName("Actualizar cuenta que no existe - English")
    void givenNotExistingAccount_whenUpdate_thenReturnError404InEnglish() {
        this.webTestClient
                .put()
                .uri(AppUtils.CUENTAS_PATH.concat("/{id}"), cuenta.getNumeroCuenta())
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_LANGUAGE, LanguageCodeEnum.EN.getCode())
                .bodyValue(cuenta)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    Assertions.assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(NotFoundErrorEnum.CUENTA_NO_ENCONTRADA.getCode());
                    Assertions.assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo(errorDetailsDTO.getErrorMessage())
                            .isEqualTo(getMessageSourceMessage(LanguageCodeEnum.EN));
                });
    }

    @Test
    @DisplayName("Eliminar cuenta que no existe")
    void givenNotExistingAccount_whenDelete_thenReturnError404() {
        this.webTestClient
                .delete()
                .uri(AppUtils.CUENTAS_PATH.concat("/{id}"), cuenta.getNumeroCuenta())
                .header(HttpHeaders.ACCEPT_LANGUAGE, LanguageCodeEnum.ES.getCode())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    Assertions.assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(NotFoundErrorEnum.CUENTA_NO_ENCONTRADA.getCode());
                    Assertions.assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo(errorDetailsDTO.getErrorMessage())
                            .isEqualTo(getMessageSourceMessage(LanguageCodeEnum.ES));
                });
    }

    @Test
    @DisplayName("Eliminar cuenta que no existe - English")
    void givenNotExistingAccount_whenDelete_thenReturnError404InEnglish() {
        this.webTestClient
                .delete()
                .uri(AppUtils.CUENTAS_PATH.concat("/{id}"), cuenta.getNumeroCuenta())
                .header(HttpHeaders.ACCEPT_LANGUAGE, LanguageCodeEnum.EN.getCode())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    Assertions.assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(NotFoundErrorEnum.CUENTA_NO_ENCONTRADA.getCode());
                    Assertions.assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo(errorDetailsDTO.getErrorMessage())
                            .isEqualTo(getMessageSourceMessage(LanguageCodeEnum.EN));
                });
    }

    @NotNull
    private String getMessageSourceMessage(LanguageCodeEnum languageCodeEnum) {
        return this.messageSource.getMessage(NotFoundErrorEnum.CUENTA_NO_ENCONTRADA.getMessage(),
                new Long[]{cuenta.getNumeroCuenta()}, new Locale(languageCodeEnum.getCode()));
    }
}
