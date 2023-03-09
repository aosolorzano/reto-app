package com.pichicha.reto.app.api.service;

import com.pichicha.reto.app.api.common.AbstractContainerBase;
import com.pichicha.reto.app.api.model.Transaction;
import com.pichicha.reto.app.api.services.TransactionService;
import com.pichicha.reto.app.api.utils.DataUtil;
import com.pichicha.reto.app.api.utils.enums.EnumState;
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
class TransactionServiceTest extends AbstractContainerBase {

    @Autowired
    TransactionService transactionService;

    private static Transaction transaction;

    @BeforeAll
    public static void init() {
        transaction = DataUtil.getTransactionTemplateDTO();
    }

    @Test
    @Order(1)
    @DisplayName("Crear transaction 1")
    void givenAccountData_whenCreate_thenReturnSavedAccountObject() {
        Mono<Transaction> nuevoMovimiento = this.transactionService.crear(transaction);
        StepVerifier.create(nuevoMovimiento)
                .assertNext(cuentaResult -> {
                    assertThat(cuentaResult.getId()).isPositive();
                    assertThat(cuentaResult.getEstado()).isEqualTo(EnumState.ACT);
                })
                .verifyComplete();
    }
}
