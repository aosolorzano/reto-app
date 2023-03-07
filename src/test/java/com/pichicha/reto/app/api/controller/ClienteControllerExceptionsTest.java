package com.pichicha.reto.app.api.controller;

import com.pichicha.reto.app.api.common.AbstractContainerBase;
import com.pichicha.reto.app.api.dto.ErrorDetailsDTO;
import com.pichicha.reto.app.api.model.Cliente;
import com.pichicha.reto.app.api.utils.AppUtils;
import com.pichicha.reto.app.api.utils.enums.LanguageCodeEnum;
import com.pichicha.reto.app.api.utils.enums.NotFoundErrorEnum;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@AutoConfigureWebTestClient
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClienteControllerExceptionsTest extends AbstractContainerBase {

    public static final String ID_NO_EXISTENTE = "2345678901";
    @Autowired
    private WebTestClient webTestClient;

    private static Cliente cliente;

    @BeforeAll
    public static void init() {
        cliente = AppUtils.getClienteTemplateObject();
        cliente.setId(ID_NO_EXISTENTE);
    }

    @Test
    @DisplayName("Buscar cliente que no existe")
    void givenNotExistingClientId_whenFindById_thenReturnError404() {
        this.webTestClient
                .get()
                .uri(AppUtils.CLIENTES_PATH.concat("/{id}"), cliente.getId())
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_LANGUAGE, LanguageCodeEnum.ES.getCode())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    Assertions.assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(NotFoundErrorEnum.CLIENTE_NO_ENCONTRADO.getCode());
                    Assertions.assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo("No se encontró el cliente con ID: " + cliente.getId() + ".");
                });
    }

    @Test
    @DisplayName("Buscar cliente que no existe - English")
    void givenNotExistingClientId_whenFindById_thenReturnError404InEnglish() {
        this.webTestClient
                .get()
                .uri(AppUtils.CLIENTES_PATH.concat("/{id}"), cliente.getId())
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_LANGUAGE, LanguageCodeEnum.EN.getCode())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    Assertions.assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(NotFoundErrorEnum.CLIENTE_NO_ENCONTRADO.getCode());
                    Assertions.assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo("Client not found with ID: " + cliente.getId() + ".");
                });
    }

    @Test
    @DisplayName("Actualizar cliente que no existe")
    void givenNotExistingClient_whenUpdate_thenReturnError404() {
        this.webTestClient
                .put()
                .uri(AppUtils.CLIENTES_PATH.concat("/{id}"), cliente.getId())
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_LANGUAGE, LanguageCodeEnum.ES.getCode())
                .bodyValue(cliente)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    Assertions.assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(NotFoundErrorEnum.CLIENTE_NO_ENCONTRADO.getCode());
                    Assertions.assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo("No se encontró el cliente con ID: " + cliente.getId() + ".");
                });
    }

    @Test
    @DisplayName("Actualizar cliente que no existe - English")
    void givenNotExistingClient_whenUpdate_thenReturnError404InEnglish() {
        this.webTestClient
                .put()
                .uri(AppUtils.CLIENTES_PATH.concat("/{id}"), cliente.getId())
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_LANGUAGE, LanguageCodeEnum.EN.getCode())
                .bodyValue(cliente)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    Assertions.assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(NotFoundErrorEnum.CLIENTE_NO_ENCONTRADO.getCode());
                    Assertions.assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo("Client not found with ID: " + cliente.getId() + ".");
                });
    }

    @Test
    @DisplayName("Eliminar cliente que no existe")
    void givenNotExistingClient_whenDelete_thenReturnError404() {
        this.webTestClient
                .delete()
                .uri(AppUtils.CLIENTES_PATH.concat("/{id}"), cliente.getId())
                .header(HttpHeaders.ACCEPT_LANGUAGE, LanguageCodeEnum.ES.getCode())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    Assertions.assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(NotFoundErrorEnum.CLIENTE_NO_ENCONTRADO.getCode());
                    Assertions.assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo("No se encontró el cliente con ID: " + cliente.getId() + ".");
                });
    }

    @Test
    @DisplayName("Eliminar cliente que no existe - English")
    void givenNotExistingClient_whenDelete_thenReturnError404InEnglish() {
        this.webTestClient
                .delete()
                .uri(AppUtils.CLIENTES_PATH.concat("/{id}"), cliente.getId())
                .header(HttpHeaders.ACCEPT_LANGUAGE, LanguageCodeEnum.EN.getCode())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    Assertions.assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(NotFoundErrorEnum.CLIENTE_NO_ENCONTRADO.getCode());
                    Assertions.assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo("Client not found with ID: " + cliente.getId() + ".");
                });
    }
}
