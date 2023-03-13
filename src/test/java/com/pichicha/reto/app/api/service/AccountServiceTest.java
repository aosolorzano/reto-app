package com.pichicha.reto.app.api.service;

import com.pichicha.reto.app.api.common.AbstractContainerBase;
import com.pichicha.reto.app.api.dto.common.StatusCriteriaDTO;
import com.pichicha.reto.app.api.exception.ResourceNotFoundException;
import com.pichicha.reto.app.api.model.Account;
import com.pichicha.reto.app.api.utils.DataUtil;
import com.pichicha.reto.app.api.utils.enums.EnumAccountType;
import com.pichicha.reto.app.api.utils.enums.EnumStatus;
import org.junit.Ignore;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountServiceTest extends AbstractContainerBase {

    private static final String CLIENTE_ID = "5678909876";

    private static Account account;

    @Autowired
    AccountService accountService;

    @BeforeAll
    public static void init() {
        account = DataUtil.getAccountTemplate();
        account.setClienteId(CLIENTE_ID);
    }

    @Test
    @Order(1)
    @DisplayName("Create")
    void givenAccountData_whenCreate_thenReturnSavedAccountObject() {
        Mono<Account> accountMono = this.accountService.create(account);
        StepVerifier.create(accountMono)
                .assertNext(accountResult -> {
                    assertThat(accountResult.getNumeroCuenta()).isPositive();
                    assertThat(accountResult.getClienteId()).isEqualTo(CLIENTE_ID);
                    assertThat(accountResult.getTipo()).isEqualTo(EnumAccountType.CTE);
                    assertThat(accountResult.getSaldo()).isEqualTo(1000.00);
                    assertThat(accountResult.getEstado()).isEqualTo(EnumStatus.ACT);
                    account.setNumeroCuenta(accountResult.getNumeroCuenta());
                })
                .verifyComplete();
    }

    @Test
    @Order(2)
    @DisplayName("Find by ID")
    void givenAccountData_whenFindById_thenReturnAccountObject() {
        Mono<Account> accountMono = this.accountService.findById(account.getNumeroCuenta());
        StepVerifier.create(accountMono)
                .assertNext(accountResult -> {
                    assertThat(accountResult.getNumeroCuenta()).isEqualTo(account.getNumeroCuenta());
                    assertThat(accountResult.getClienteId()).isEqualTo(CLIENTE_ID);
                    assertThat(accountResult.getTipo()).isEqualTo(EnumAccountType.CTE);
                    assertThat(accountResult.getSaldo()).isEqualTo(1000.00);
                    assertThat(accountResult.getEstado()).isEqualTo(EnumStatus.ACT);
                })
                .verifyComplete();
    }

    @Test
    @Order(3)
    @DisplayName("Update status")
    void givenAccountData_whenUpdateStatus_thenReturnSavedAccountObject() {
        StatusCriteriaDTO statusCriteriaDTO = StatusCriteriaDTO.builder()
                .id(account.getNumeroCuenta())
                .status(EnumStatus.INA)
                .build();
        Mono<Void> voidMono = this.accountService.updateStatus(statusCriteriaDTO);
        StepVerifier.create(voidMono)
                .verifyComplete();
    }

    @Test
    @Order(4)
    @DisplayName("Delete")
    void givenAccountData_whenDeleteById_thenDeleteAccountObject() {
        Mono<Void> voidMono = this.accountService.delete(account.getNumeroCuenta());
        StepVerifier.create(voidMono)
                .verifyComplete();
    }

    @Test
    @Order(5)
    @DisplayName("Find deleted account")
    void givenDeletedClientData_whenFindById_thenReturnNull() {
        Mono<Account> accountMono = this.accountService.findById(account.getNumeroCuenta());
        StepVerifier.create(accountMono)
                .expectError(ResourceNotFoundException.class)
                .verify();
    }

    @Test
    @Order(6)
    @Ignore("Ignore until criteria is implemented")
    @DisplayName("Find by client ID")
    void givenAccountData_whenFindByClientId_thenReturnAccountObject() {

    }
}
