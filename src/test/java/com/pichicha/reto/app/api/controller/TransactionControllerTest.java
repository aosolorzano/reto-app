package com.pichicha.reto.app.api.controller;

import com.pichicha.reto.app.api.common.AbstractContainerBase;
import com.pichicha.reto.app.api.model.Transaction;
import com.pichicha.reto.app.api.utils.ControllerUtil;
import com.pichicha.reto.app.api.utils.DataUtil;
import com.pichicha.reto.app.api.utils.enums.EnumStatus;
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
class TransactionControllerTest extends AbstractContainerBase {

    @Autowired
    private WebTestClient webTestClient;

    private static Transaction transaction;

    @BeforeAll
    public static void init() {
        transaction = DataUtil.getTransactionTemplateDTO();
    }

    @Test
    @Order(1)
    @DisplayName("Crear transaction 1")
    void givenMovimientoData_whenCreate_thenReturnCreatedObject() {
        this.webTestClient
                .post()
                .uri(ControllerUtil.TRANSACTION_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(transaction)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Transaction.class)
                .value(movimientoResult -> {
                    Assertions.assertThat(movimientoResult.getId()).isNotNull().isPositive();
                    Assertions.assertThat(movimientoResult.getEstado()).isEqualTo(EnumStatus.ACT);
                    BeanUtils.copyProperties(movimientoResult, transaction);
                });
    }
}
