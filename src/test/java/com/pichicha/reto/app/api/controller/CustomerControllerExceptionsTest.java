package com.pichicha.reto.app.api.controller;

import com.pichicha.reto.app.api.common.AbstractContainerBase;
import com.pichicha.reto.app.api.dto.CustomerDTO;
import com.pichicha.reto.app.api.dto.ErrorDetailsDTO;
import com.pichicha.reto.app.api.dto.CustomerCriteriaDTO;
import com.pichicha.reto.app.api.utils.ControllerUtil;
import com.pichicha.reto.app.api.utils.DataUtil;
import com.pichicha.reto.app.api.utils.enums.EnumLanguageCode;
import com.pichicha.reto.app.api.utils.enums.EnumAppError;
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
class CustomerControllerExceptionsTest extends AbstractContainerBase {

    public static final String NON_EXISTING_ID = "2345678901";
    @Autowired
    private WebTestClient webTestClient;

    private static CustomerDTO customerDTO;

    @BeforeAll
    public static void init() {
        customerDTO = DataUtil.getCustomerTemplateDTO();
        customerDTO.setId(NON_EXISTING_ID);
    }

    @Test
    @DisplayName("Find non existing customer - Spanish")
    void givenNonExistingCustomer_whenFindById_thenReturnError404() {
        var customerCriteriaDTO = CustomerCriteriaDTO.builder()
                .id(NON_EXISTING_ID)
                .build();
        this.webTestClient
                .post()
                .uri(ControllerUtil.CUSTOMER_PATH.concat(ControllerUtil.FIND_PATH))
                .bodyValue(customerCriteriaDTO)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_LANGUAGE, EnumLanguageCode.ES.getCode())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    Assertions.assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumAppError.CUSTOMER_NOT_FOUND.getCode());
                    Assertions.assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo("No se encontró el cliente con ID: " + customerCriteriaDTO.getId() + ".");
                });
    }

    @Test
    @DisplayName("Find non existing customer - English")
    void givenNonExistingCustomer_whenFindById_thenReturnError404InEnglish() {
        var customerCriteriaDTO = CustomerCriteriaDTO.builder()
                .id(NON_EXISTING_ID)
                .build();
        this.webTestClient
                .post()
                .uri(ControllerUtil.CUSTOMER_PATH.concat(ControllerUtil.FIND_PATH))
                .bodyValue(customerCriteriaDTO)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_LANGUAGE, EnumLanguageCode.EN.getCode())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    Assertions.assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumAppError.CUSTOMER_NOT_FOUND.getCode());
                    Assertions.assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo("Customer not found with ID: " + customerDTO.getId() + ".");
                });
    }

    @Test
    @DisplayName("Update non existing customer - Spanish")
    void givenNonExistingCustomer_whenUpdate_thenReturnError404() {
        this.webTestClient
                .put()
                .uri(ControllerUtil.CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_LANGUAGE, EnumLanguageCode.ES.getCode())
                .bodyValue(customerDTO)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    Assertions.assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumAppError.CUSTOMER_NOT_FOUND.getCode());
                    Assertions.assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo("No se encontró el cliente con ID: " + customerDTO.getId() + ".");
                });
    }

    @Test
    @DisplayName("Update non existing customer - English")
    void givenNonExistingCustomer_whenUpdate_thenReturnError404InEnglish() {
        this.webTestClient
                .put()
                .uri(ControllerUtil.CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_LANGUAGE, EnumLanguageCode.EN.getCode())
                .bodyValue(customerDTO)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    Assertions.assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumAppError.CUSTOMER_NOT_FOUND.getCode());
                    Assertions.assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo("Customer not found with ID: " + customerDTO.getId() + ".");
                });
    }

    @Test
    @DisplayName("Delete non existing customer - Spanish")
    void givenNonExistingCustomer_whenDelete_thenReturnError404() {
        this.webTestClient
                .post()
                .uri(ControllerUtil.CUSTOMER_PATH.concat(ControllerUtil.DELETE_PATH))
                .bodyValue(customerDTO.getId())
                .header(HttpHeaders.ACCEPT_LANGUAGE, EnumLanguageCode.ES.getCode())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    Assertions.assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumAppError.CUSTOMER_NOT_FOUND.getCode());
                    Assertions.assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo("No se encontró el cliente con ID: " + customerDTO.getId() + ".");
                });
    }

    @Test
    @DisplayName("Delete non existing customer - English")
    void givenNonExistingCustomer_whenDelete_thenReturnError404InEnglish() {
        this.webTestClient
                .post()
                .uri(ControllerUtil.CUSTOMER_PATH.concat(ControllerUtil.DELETE_PATH))
                .bodyValue(customerDTO.getId())
                .header(HttpHeaders.ACCEPT_LANGUAGE, EnumLanguageCode.EN.getCode())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    Assertions.assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumAppError.CUSTOMER_NOT_FOUND.getCode());
                    Assertions.assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo("Customer not found with ID: " + customerDTO.getId() + ".");
                });
    }
}
