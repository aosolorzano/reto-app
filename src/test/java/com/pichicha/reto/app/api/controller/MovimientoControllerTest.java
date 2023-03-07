package com.pichicha.reto.app.api.controller;

import com.pichicha.reto.app.api.common.AbstractContainerBase;
import com.pichicha.reto.app.api.model.Movimiento;
import com.pichicha.reto.app.api.utils.AppUtils;
import com.pichicha.reto.app.api.utils.enums.EstadoEnum;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.BeanUtils;
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
class MovimientoControllerTest extends AbstractContainerBase {

    @Autowired
    private WebTestClient webTestClient;

    private static Movimiento movimiento;

    @BeforeAll
    public static void init() {
        movimiento = AppUtils.getMovimientoTemplateObject();
    }

    @Test
    @Order(1)
    @DisplayName("Crear movimiento 1")
    void givenMovimientoData_whenCreate_thenReturnCreatedObject() {
        this.webTestClient
                .post()
                .uri(AppUtils.MOVIMIENTOS_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(movimiento)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Movimiento.class)
                .value(movimientoResult -> {
                    Assertions.assertThat(movimientoResult.getId()).isNotNull().isPositive();
                    Assertions.assertThat(movimientoResult.getEstado()).isEqualTo(EstadoEnum.ACT);
                    BeanUtils.copyProperties(movimientoResult, movimiento);
                });
    }
}
