package com.pichicha.reto.app.api.service;

import com.pichicha.reto.app.api.common.AbstractContainerBase;
import com.pichicha.reto.app.api.exception.ResourceNotFoundException;
import com.pichicha.reto.app.api.model.Cuenta;
import com.pichicha.reto.app.api.services.CuentaService;
import com.pichicha.reto.app.api.utils.AppUtils;
import com.pichicha.reto.app.api.utils.enums.EstadoEnum;
import com.pichicha.reto.app.api.utils.enums.TipoCuentaEnum;
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
class CuentaServiceTest extends AbstractContainerBase {

    private static final String CLIENTE_ID = "5678909876";

    private static Cuenta cuenta;

    @Autowired
    CuentaService cuentaService;

    @BeforeAll
    public static void init() {
        cuenta = AppUtils.getCuentaTemplateObject();
        cuenta.setClienteId(CLIENTE_ID);
    }

    @Test
    @Order(1)
    @DisplayName("Crear")
    void givenAccountData_whenCreate_thenReturnSavedAccountObject() {
        Mono<Cuenta> cuentaCreada = this.cuentaService.crear(cuenta);
        StepVerifier.create(cuentaCreada)
                .assertNext(cuentaResult -> {
                    assertThat(cuentaResult.getNumeroCuenta()).isPositive();
                    assertThat(cuentaResult.getClienteId()).isEqualTo(CLIENTE_ID);
                    assertThat(cuentaResult.getTipo()).isEqualTo(TipoCuentaEnum.CTE);
                    assertThat(cuentaResult.getSaldo()).isEqualTo(1000.00);
                    assertThat(cuentaResult.getEstado()).isEqualTo(EstadoEnum.ACT);
                    cuenta.setNumeroCuenta(cuentaResult.getNumeroCuenta());
                })
                .verifyComplete();
    }

    @Test
    @Order(2)
    @DisplayName("Busca por ID")
    void givenAccountData_whenFindById_thenReturnAccountObject() {
        Mono<Cuenta> cuentaCreada = this.cuentaService.buscarPorId(cuenta.getNumeroCuenta());
        StepVerifier.create(cuentaCreada)
                .assertNext(cuentaResult -> {
                    assertThat(cuentaResult.getNumeroCuenta()).isEqualTo(cuenta.getNumeroCuenta());
                    assertThat(cuentaResult.getClienteId()).isEqualTo(CLIENTE_ID);
                    assertThat(cuentaResult.getTipo()).isEqualTo(TipoCuentaEnum.CTE);
                    assertThat(cuentaResult.getSaldo()).isEqualTo(1000.00);
                    assertThat(cuentaResult.getEstado()).isEqualTo(EstadoEnum.ACT);
                })
                .verifyComplete();
    }

    @Test
    @Order(3)
    @DisplayName("Busca por cliente ID")
    void givenAccountData_whenFindByClientId_thenReturnAccountObject() {
        Flux<Cuenta> cuentas = this.cuentaService.buscarPorCliente(CLIENTE_ID);
        StepVerifier.create(cuentas)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    @Order(4)
    @DisplayName("Actualiza estado")
    void givenAccountData_whenUpdateState_thenReturnSavedAccountObject() {
        cuenta.setEstado(EstadoEnum.INA);
        Mono<Cuenta> cuentaActualizada = this.cuentaService.actualizar(cuenta.getNumeroCuenta(), cuenta);
        StepVerifier.create(cuentaActualizada)
                .assertNext(cuentaResult -> {
                    assertThat(cuentaResult.getNumeroCuenta()).isEqualTo(cuenta.getNumeroCuenta());
                    assertThat(cuentaResult.getClienteId()).isEqualTo(CLIENTE_ID);
                    assertThat(cuentaResult.getTipo()).isEqualTo(TipoCuentaEnum.CTE);
                    assertThat(cuentaResult.getSaldo()).isEqualTo(1000.00);
                    assertThat(cuentaResult.getEstado()).isEqualTo(EstadoEnum.INA);
                })
                .verifyComplete();
    }

    @Test
    @Order(5)
    @DisplayName("Elimina cuenta por ID")
    void givenAccountData_whenDeleteById_thenDeleteAccountObject() {
        Mono<Void> cuentaEliminada = this.cuentaService.eliminar(cuenta.getNumeroCuenta());
        StepVerifier.create(cuentaEliminada)
                .verifyComplete();
    }

    @Test
    @Order(5)
    @DisplayName("Busca cuenta eliminada por ID")
    void givenDeletedClientData_whenFindById_thenReturnNull() {
        Mono<Cuenta> cuentaEliminada = this.cuentaService.buscarPorId(cuenta.getNumeroCuenta());
        StepVerifier.create(cuentaEliminada)
                .expectError(ResourceNotFoundException.class)
                .verify();
    }
}
