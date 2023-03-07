package com.pichicha.reto.app.api.service;

import com.pichicha.reto.app.api.common.AbstractContainerBase;
import com.pichicha.reto.app.api.model.Movimiento;
import com.pichicha.reto.app.api.services.MovimientoService;
import com.pichicha.reto.app.api.utils.AppUtils;
import com.pichicha.reto.app.api.utils.enums.EstadoEnum;
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
class MovimientoServiceTest extends AbstractContainerBase {

    @Autowired
    MovimientoService movimientoService;

    private static Movimiento movimiento;

    @BeforeAll
    public static void init() {
        movimiento = AppUtils.getMovimientoTemplateObject();
    }

    @Test
    @Order(1)
    @DisplayName("Crear movimiento 1")
    void givenAccountData_whenCreate_thenReturnSavedAccountObject() {
        Mono<Movimiento> nuevoMovimiento = this.movimientoService.crear(movimiento);
        StepVerifier.create(nuevoMovimiento)
                .assertNext(cuentaResult -> {
                    assertThat(cuentaResult.getId()).isPositive();
                    assertThat(cuentaResult.getEstado()).isEqualTo(EstadoEnum.ACT);
                })
                .verifyComplete();
    }
}
