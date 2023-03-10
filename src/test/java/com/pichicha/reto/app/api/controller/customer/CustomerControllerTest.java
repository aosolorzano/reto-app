package com.pichicha.reto.app.api.controller.customer;

import com.pichicha.reto.app.api.common.AbstractContainerBase;
import com.pichicha.reto.app.api.dto.customer.*;
import com.pichicha.reto.app.api.utils.ControllerUtil;
import com.pichicha.reto.app.api.utils.DataUtil;
import com.pichicha.reto.app.api.utils.enums.EnumGenre;
import com.pichicha.reto.app.api.utils.enums.EnumStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerTest extends AbstractContainerBase {

    private static final String CUSTOMER_ID = "4561237890";

    @Autowired
    private WebTestClient webTestClient;

    private static CustomerDTO customerDTO;

    @BeforeAll
    public static void init() {
        customerDTO = DataUtil.getCustomerTemplateDTO();
        customerDTO.setId(CUSTOMER_ID);
    }

    @Test
    @Order(1)
    @DisplayName("Create customer")
    void givenCustomerData_whenCreate_thenCreateCustomerObject() {
        this.webTestClient
                .post()
                .uri(ControllerUtil.CUSTOMER_PATH)
                .bodyValue(customerDTO)
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    @Order(2)
    @DisplayName("Find created customer")
    void givenCustomerData_whenFind_thenReturnCustomerObject() {
        this.webTestClient
                .post()
                .uri(ControllerUtil.CUSTOMER_PATH.concat(ControllerUtil.FIND_PATH))
                .bodyValue(new CustomerIdDTO(customerDTO.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(CustomerDTO.class)
                .value(customerResponse -> {
                    assertThat(customerResponse.getId()).isEqualTo(CUSTOMER_ID);
                    assertThat(customerResponse.getNombre()).isEqualTo("Andres Solorzano");
                    assertThat(customerResponse.getGenero()).isEqualTo(EnumGenre.M);
                    assertThat(customerResponse.getEdad()).isEqualTo(37);
                    assertThat(customerResponse.getDireccion()).isEqualTo("Street 123");
                    assertThat(customerResponse.getTelefono()).isEqualTo("0987654321");
                });
    }

    @Test
    @Order(3)
    @DisplayName("Update customer")
    void givenCustomerData_whenModifyData_thenUpdateCustomerObject() {
        customerDTO.setNombre("Updated Name");
        customerDTO.setTelefono("1234567890");
        customerDTO.setDireccion("Updated Address");
        this.webTestClient
                .put()
                .uri(ControllerUtil.CUSTOMER_PATH)
                .bodyValue(customerDTO)
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    @Order(4)
    @DisplayName("Find updated customer")
    void givenUpdatedCustomerData_whenFindById_thenReturnCustomerObject() {
        this.webTestClient
                .post()
                .uri(ControllerUtil.CUSTOMER_PATH.concat(ControllerUtil.FIND_PATH))
                .bodyValue(new CustomerIdDTO(customerDTO.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(CustomerDTO.class)
                .value(customerResponse -> {
                    assertThat(customerResponse.getId()).isEqualTo(CUSTOMER_ID);
                    assertThat(customerResponse.getNombre()).isEqualTo("Updated Name");
                    assertThat(customerResponse.getDireccion()).isEqualTo("Updated Address");
                    assertThat(customerResponse.getTelefono()).isEqualTo("1234567890");
                });
    }

    @Test
    @Order(5)
    @DisplayName("Update status")
    void givenCustomerData_whenUpdateStatus_thenUpdateCustomerObject() {
        var customerStatusDTO = CustomerStatusDTO.builder()
                .id(CUSTOMER_ID)
                .status(EnumStatus.ACT)
                .build();
        this.webTestClient
                .put()
                .uri(ControllerUtil.CUSTOMER_PATH.concat(ControllerUtil.STATUS_PATH))
                .bodyValue(customerStatusDTO)
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    @Order(6)
    @DisplayName("Update password")
    void givenCustomerData_whenUpdatePasswd_thenUpdateCustomerObject() {
        var customerPasswordDTO = CustomerPasswordDTO.builder()
                .id(CUSTOMER_ID)
                .password("123456abc")
                .build();
        this.webTestClient
                .put()
                .uri(ControllerUtil.CUSTOMER_PATH.concat("/password"))
                .bodyValue(customerPasswordDTO)
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    @Order(7)
    @DisplayName("Delete customer")
    void givenCustomerData_whenDeleteById_thenDeleteCustomerObject() {
        this.webTestClient
                .post()
                .uri(ControllerUtil.CUSTOMER_PATH.concat(ControllerUtil.DELETE_PATH))
                .bodyValue(new CustomerIdDTO(customerDTO.getId()))
                .exchange()
                .expectStatus().is2xxSuccessful();
    }
}
