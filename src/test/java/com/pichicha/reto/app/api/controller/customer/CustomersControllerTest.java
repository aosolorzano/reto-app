package com.pichicha.reto.app.api.controller.customer;

import com.pichicha.reto.app.api.common.AbstractContainerBase;
import com.pichicha.reto.app.api.dto.customer.CustomerCriteriaDTO;
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
class CustomersControllerTest extends AbstractContainerBase {

    private static final String CUSTOMER_ID = "1234567890";

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    @DisplayName("Find customers by Id")
    void givenCustomerData_whenFindById_thenReturnCustomerList() {
        CustomerCriteriaDTO customerCriteriaDTO = CustomerCriteriaDTO.builder()
                .id(CUSTOMER_ID)
                .build();
        this.webTestClient
                .post()
                .uri(ControllerUtil.CUSTOMERS_PATH)
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
    @DisplayName("Find customers by Name")
    void givenCustomerData_whenFindByName_thenReturnCustomerList() {
        CustomerCriteriaDTO customerCriteriaDTO = CustomerCriteriaDTO.builder()
                .name("Juan")
                .build();
        this.webTestClient
                .post()
                .uri(ControllerUtil.CUSTOMERS_PATH)
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
    @DisplayName("Find customers by Last Name")
    void givenCustomerData_whenFindByLastName_thenReturnCustomerList() {
        CustomerCriteriaDTO customerCriteriaDTO = CustomerCriteriaDTO.builder()
                .name("Osorio")
                .build();
        this.webTestClient
                .post()
                .uri(ControllerUtil.CUSTOMERS_PATH)
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
    @Order(4)
    @DisplayName("Find customers Id starting with 09")
    void givenCustomerData_whenFindByIdCriteria_thenReturnCustomerList() {
        CustomerCriteriaDTO customerCriteriaDTO = CustomerCriteriaDTO.builder()
                .id("09")
                .build();
        this.webTestClient
                .post()
                .uri(ControllerUtil.CUSTOMERS_PATH)
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
    @Order(5)
    @DisplayName("Find customers name starting with J")
    void givenCustomerData_whenFindByNameCriteria_thenReturnCustomerList() {
        CustomerCriteriaDTO customerCriteriaDTO = CustomerCriteriaDTO.builder()
                .name("J")
                .build();
        this.webTestClient
                .post()
                .uri(ControllerUtil.CUSTOMERS_PATH)
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
