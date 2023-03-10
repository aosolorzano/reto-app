package com.pichicha.reto.app.api.service;

import com.pichicha.reto.app.api.common.AbstractContainerBase;
import com.pichicha.reto.app.api.dto.customer.CustomerCriteriaDTO;
import com.pichicha.reto.app.api.dto.customer.CustomerDTO;
import com.pichicha.reto.app.api.dto.customer.CustomerPasswordDTO;
import com.pichicha.reto.app.api.dto.customer.CustomerStatusDTO;
import com.pichicha.reto.app.api.exception.ResourceNotFoundException;
import com.pichicha.reto.app.api.utils.DataUtil;
import com.pichicha.reto.app.api.utils.enums.EnumGenre;
import com.pichicha.reto.app.api.utils.enums.EnumStatus;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerServiceTest extends AbstractContainerBase {

    private static final String CUSTOMER_ID = "4561237890";

    private static CustomerDTO customerDTO;

    @Autowired
    CustomerService customerService;

    @BeforeAll
    public static void init() {
        customerDTO = DataUtil.getCustomerTemplateDTO();
        customerDTO.setId(CUSTOMER_ID);
    }

    @Test
    @Order(1)
    @DisplayName("Create customer")
    void givenCustomerData_whenSave_thenCreateCustomerObject() {
        Mono<Void> response = this.customerService.create(Mono.just(customerDTO));
        StepVerifier.create(response)
                .verifyComplete();
    }

    @Test
    @Order(2)
    @DisplayName("Find created customer")
    void givenCustomerData_whenFindById_thenReturnCustomerObject() {
        Mono<CustomerDTO> customerDTOMono = this.customerService.findById(CUSTOMER_ID);
        StepVerifier.create(customerDTOMono)
                .assertNext(customerDTO -> {
                    assertThat(customerDTO.getId()).isEqualTo(CUSTOMER_ID);
                    assertThat(customerDTO.getNombre()).isEqualTo("Andres Solorzano");
                    assertThat(customerDTO.getGenero()).isEqualTo(EnumGenre.M);
                    assertThat(customerDTO.getEdad()).isEqualTo(37);
                    assertThat(customerDTO.getDireccion()).isEqualTo("Street 123");
                    assertThat(customerDTO.getTelefono()).isEqualTo("0987654321");
                })
                .verifyComplete();
    }

    @Test
    @Order(3)
    @DisplayName("Update customer")
    void givenCustomerData_whenModifyData_thenUpdateCustomerObject() {
        customerDTO.setNombre("Updated Name");
        customerDTO.setTelefono("1234567890");
        customerDTO.setDireccion("Updated Address");
        Mono<Void> response = this.customerService.update(customerDTO);
        StepVerifier.create(response)
                .verifyComplete();
    }

    @Test
    @Order(4)
    @DisplayName("Find updated customer")
    void givenUpdatedCustomerData_whenFindById_thenReturnCustomerObject() {
        Mono<CustomerDTO> customerDTOMono = this.customerService.findById(CUSTOMER_ID);
        StepVerifier.create(customerDTOMono)
                .assertNext(customerDTO -> {
                    assertThat(customerDTO.getId()).isEqualTo(CUSTOMER_ID);
                    assertThat(customerDTO.getNombre()).isEqualTo("Updated Name");
                    assertThat(customerDTO.getTelefono()).isEqualTo("1234567890");
                    assertThat(customerDTO.getDireccion()).isEqualTo("Updated Address");
                })
                .verifyComplete();
    }

    @Test
    @Order(5)
    @DisplayName("Update status")
    void givenCustomerData_whenUpdateStatus_thenUpdateCustomerObject() {
        var customerStatusDTO = CustomerStatusDTO.builder()
                .id(CUSTOMER_ID)
                .status(EnumStatus.ACT)
                .build();
        Mono<Void> voidMono = this.customerService.updateStatus(customerStatusDTO);
        StepVerifier.create(voidMono)
                .verifyComplete();
    }

    @Test
    @Order(6)
    @DisplayName("Update password")
    void givenCustomerData_whenUpdatePasswd_thenUpdateCustomerObject() {
        var customerPasswordDTO = CustomerPasswordDTO.builder()
                .id(CUSTOMER_ID)
                .password("123456abc")
                .build();
        Mono<Void> voidMono = this.customerService.updatePassword(customerPasswordDTO);
        StepVerifier.create(voidMono)
                .verifyComplete();
    }

    @Test
    @Order(7)
    @DisplayName("Delete customer")
    void givenCustomerData_whenDeleteById_thenDeleteCustomerObject() {
        Mono<Void> voidMono = this.customerService.delete(CUSTOMER_ID);
        StepVerifier.create(voidMono)
                .verifyComplete();
    }

    @Test
    @Order(8)
    @DisplayName("Find deleted customer")
    void givenDeletedCustomerData_whenFindById_thenReturnException() {
        Mono<CustomerDTO> customerDTOMono = this.customerService.findById(CUSTOMER_ID);
        StepVerifier.create(customerDTOMono)
                .expectError(ResourceNotFoundException.class)
                .verify();
    }

    @Test
    @Order(9)
    @DisplayName("Find customer by Name")
    void givenCustomerData_whenFindByName_thenReturnCustomerObjects() {
        CustomerCriteriaDTO customerCriteriaDTO = CustomerCriteriaDTO.builder()
                .name("Jose")
                .build();
        Flux<CustomerDTO> taskFluxResult = this.customerService.find(customerCriteriaDTO);
        StepVerifier.create(taskFluxResult)
                .expectNextCount(1L)
                .verifyComplete();
    }
}
