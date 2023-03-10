package com.pichicha.reto.app.api.controller.account;

import com.pichicha.reto.app.api.common.AbstractContainerBase;
import com.pichicha.reto.app.api.dto.account.AccountCriteriaDTO;
import com.pichicha.reto.app.api.dto.customer.CustomerDTO;
import com.pichicha.reto.app.api.utils.ControllerUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountsControllerTest extends AbstractContainerBase {

    private static final Long ACCOUNT_NUMBER = 478758L;
    private static final String CUSTOMER_ID = "1234567890";

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    @DisplayName("Find by account number")
    void givenAccountData_whenFindByAccountId_thenReturnCustomerList() {
        AccountCriteriaDTO customerCriteriaDTO = AccountCriteriaDTO.builder()
                .accountNumber(ACCOUNT_NUMBER)
                .build();
        this.webTestClient
                .post()
                .uri(ControllerUtil.ACCOUNTS_PATH)
                .bodyValue(customerCriteriaDTO)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(CustomerDTO.class)
                .value(customerList -> {
                    Assertions.assertThat(customerList).isNotEmpty();
                    Assertions.assertThat(customerList).hasSize(1);
                });
    }

    @Test
    @Order(2)
    @DisplayName("Find by customer ID")
    void givenAccountData_whenFindByCustomerId_thenReturnAccountList() {
        AccountCriteriaDTO customerCriteriaDTO = AccountCriteriaDTO.builder()
                .customerId(CUSTOMER_ID)
                .build();
        this.webTestClient
                .post()
                .uri(ControllerUtil.ACCOUNTS_PATH)
                .bodyValue(customerCriteriaDTO)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(CustomerDTO.class)
                .value(customerList -> {
                    Assertions.assertThat(customerList).isNotEmpty();
                    Assertions.assertThat(customerList).hasSize(1);
                });
    }

    @Test
    @Order(3)
    @DisplayName("Find with customers ID starting with 09")
    void givenAccountData_whenFindByCustomerCriteria_thenReturnAccountList() {
        AccountCriteriaDTO customerCriteriaDTO = AccountCriteriaDTO.builder()
                .customerId("09")
                .build();
        this.webTestClient
                .post()
                .uri(ControllerUtil.ACCOUNTS_PATH)
                .bodyValue(customerCriteriaDTO)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(CustomerDTO.class)
                .value(customerList -> {
                    Assertions.assertThat(customerList).isNotEmpty();
                    Assertions.assertThat(customerList).hasSize(2);
                });
    }
}
