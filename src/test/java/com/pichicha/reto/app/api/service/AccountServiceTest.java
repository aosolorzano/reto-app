package com.pichicha.reto.app.api.service;

import com.pichicha.reto.app.api.common.AbstractContainerBase;
import com.pichicha.reto.app.api.exception.ResourceNotFoundException;
import com.pichicha.reto.app.api.model.Account;
import com.pichicha.reto.app.api.services.AccountService;
import com.pichicha.reto.app.api.utils.DataUtil;
import com.pichicha.reto.app.api.utils.enums.EnumState;
import com.pichicha.reto.app.api.utils.enums.EnumAccountType;
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
class AccountServiceTest extends AbstractContainerBase {

    private static final String CLIENTE_ID = "5678909876";

    private static Account account;

    @Autowired
    AccountService accountService;

    @BeforeAll
    public static void init() {
        account = DataUtil.getAccountTemplateDTO();
        account.setClienteId(CLIENTE_ID);
    }

    @Test
    @Order(1)
    @DisplayName("Crear")
    void givenAccountData_whenCreate_thenReturnSavedAccountObject() {
        Mono<Account> cuentaCreada = this.accountService.crear(account);
        StepVerifier.create(cuentaCreada)
                .assertNext(cuentaResult -> {
                    assertThat(cuentaResult.getNumeroCuenta()).isPositive();
                    assertThat(cuentaResult.getClienteId()).isEqualTo(CLIENTE_ID);
                    assertThat(cuentaResult.getTipo()).isEqualTo(EnumAccountType.CTE);
                    assertThat(cuentaResult.getSaldo()).isEqualTo(1000.00);
                    assertThat(cuentaResult.getEstado()).isEqualTo(EnumState.ACT);
                    account.setNumeroCuenta(cuentaResult.getNumeroCuenta());
                })
                .verifyComplete();
    }

    @Test
    @Order(2)
    @DisplayName("Busca por ID")
    void givenAccountData_whenFindById_thenReturnAccountObject() {
        Mono<Account> cuentaCreada = this.accountService.buscarPorId(account.getNumeroCuenta());
        StepVerifier.create(cuentaCreada)
                .assertNext(cuentaResult -> {
                    assertThat(cuentaResult.getNumeroCuenta()).isEqualTo(account.getNumeroCuenta());
                    assertThat(cuentaResult.getClienteId()).isEqualTo(CLIENTE_ID);
                    assertThat(cuentaResult.getTipo()).isEqualTo(EnumAccountType.CTE);
                    assertThat(cuentaResult.getSaldo()).isEqualTo(1000.00);
                    assertThat(cuentaResult.getEstado()).isEqualTo(EnumState.ACT);
                })
                .verifyComplete();
    }

    @Test
    @Order(3)
    @DisplayName("Busca por cliente ID")
    void givenAccountData_whenFindByClientId_thenReturnAccountObject() {
        Flux<Account> cuentas = this.accountService.buscarPorCliente(CLIENTE_ID);
        StepVerifier.create(cuentas)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    @Order(4)
    @DisplayName("Actualiza estado")
    void givenAccountData_whenUpdateState_thenReturnSavedAccountObject() {
        account.setEstado(EnumState.INA);
        Mono<Account> cuentaActualizada = this.accountService.actualizar(account.getNumeroCuenta(), account);
        StepVerifier.create(cuentaActualizada)
                .assertNext(cuentaResult -> {
                    assertThat(cuentaResult.getNumeroCuenta()).isEqualTo(account.getNumeroCuenta());
                    assertThat(cuentaResult.getClienteId()).isEqualTo(CLIENTE_ID);
                    assertThat(cuentaResult.getTipo()).isEqualTo(EnumAccountType.CTE);
                    assertThat(cuentaResult.getSaldo()).isEqualTo(1000.00);
                    assertThat(cuentaResult.getEstado()).isEqualTo(EnumState.INA);
                })
                .verifyComplete();
    }

    @Test
    @Order(5)
    @DisplayName("Elimina account por ID")
    void givenAccountData_whenDeleteById_thenDeleteAccountObject() {
        Mono<Void> cuentaEliminada = this.accountService.eliminar(account.getNumeroCuenta());
        StepVerifier.create(cuentaEliminada)
                .verifyComplete();
    }

    @Test
    @Order(5)
    @DisplayName("Busca account eliminada por ID")
    void givenDeletedClientData_whenFindById_thenReturnNull() {
        Mono<Account> cuentaEliminada = this.accountService.buscarPorId(account.getNumeroCuenta());
        StepVerifier.create(cuentaEliminada)
                .expectError(ResourceNotFoundException.class)
                .verify();
    }
}
