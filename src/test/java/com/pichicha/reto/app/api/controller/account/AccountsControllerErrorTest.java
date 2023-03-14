package com.pichicha.reto.app.api.controller.account;

import com.pichicha.reto.app.api.common.AbstractContainerBase;
import com.pichicha.reto.app.api.dto.account.AccountCriteriaDTO;
import com.pichicha.reto.app.api.dto.common.ErrorDetailsDTO;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountsControllerErrorTest extends AbstractContainerBase {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    @DisplayName("Find accounts with empty criteria - Spanish")
    void givenAccountsData_whenFindByEmptyCriteria_thenReturnError() {
        AccountCriteriaDTO customerCriteriaDTO = AccountCriteriaDTO.builder().build();
        this.webTestClient
                .post()
                .uri(ControllerUtil.ACCOUNTS_PATH)
                .bodyValue(customerCriteriaDTO)
                .header(HttpHeaders.ACCEPT_LANGUAGE, EnumLanguageCode.ES.getCode())
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumValidationError.NO_CRITERIA_FOUND.getCode());
                    assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo("No se encontraron parámetros de búsqueda.");
                });
    }

    @Test
    @Order(2)
    @DisplayName("Find accounts with empty criteria - English")
    void givenAccountsData_whenFindByEmptyCriteria_thenReturnErrorInEnglish() {
        AccountCriteriaDTO customerCriteriaDTO = AccountCriteriaDTO.builder().build();
        this.webTestClient
                .post()
                .uri(ControllerUtil.ACCOUNTS_PATH)
                .bodyValue(customerCriteriaDTO)
                .header(HttpHeaders.ACCEPT_LANGUAGE, EnumLanguageCode.EN.getCode())
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ErrorDetailsDTO.class)
                .value(errorDetailsDTO -> {
                    assertThat(errorDetailsDTO.getErrorCode())
                            .isEqualTo(EnumValidationError.NO_CRITERIA_FOUND.getCode());
                    assertThat(errorDetailsDTO.getErrorMessage())
                            .isEqualTo("No search parameters were found.");
                });
    }
}
