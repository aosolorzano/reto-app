package com.pichicha.reto.app.api.controller.customer;

import com.pichicha.reto.app.api.common.AbstractContainerBase;
import com.pichicha.reto.app.api.dto.common.ErrorDetailsDTO;
import com.pichicha.reto.app.api.dto.customer.CustomerCriteriaDTO;
import com.pichicha.reto.app.api.utils.ControllerUtil;
import com.pichicha.reto.app.api.utils.enums.EnumAppError;
import com.pichicha.reto.app.api.utils.enums.EnumLanguageCode;
import com.pichicha.reto.app.api.utils.enums.EnumValidationError;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomersControllerErrorTest extends AbstractContainerBase {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    @DisplayName("Find customers by empty criteria - Spanish")
    void givenCustomerData_whenFindByEmptyCriteria_thenReturnError() {
        CustomerCriteriaDTO customerCriteriaDTO = CustomerCriteriaDTO.builder().build();
        this.webTestClient
                .post()
                .uri(ControllerUtil.CUSTOMERS_PATH)
                .bodyValue(customerCriteriaDTO)
                .header(HttpHeaders.ACCEPT_LANGUAGE, EnumLanguageCode.ES.getCode())
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    Assertions.assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumAppError.NO_CRITERIA_FOUND.getCode());
                    Assertions.assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo("No se encontraron parámetros de búsqueda.");
                });
    }

    @Test
    @Order(2)
    @DisplayName("Find customers by empty criteria - English")
    void givenCustomerData_whenFindByEmptyCriteria_thenReturnErrorInEnglish() {
        CustomerCriteriaDTO customerCriteriaDTO = CustomerCriteriaDTO.builder().build();
        this.webTestClient
                .post()
                .uri(ControllerUtil.CUSTOMERS_PATH)
                .bodyValue(customerCriteriaDTO)
                .header(HttpHeaders.ACCEPT_LANGUAGE, EnumLanguageCode.EN.getCode())
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    Assertions.assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumAppError.NO_CRITERIA_FOUND.getCode());
                    Assertions.assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo("No search parameters were found.");
                });
    }
}
