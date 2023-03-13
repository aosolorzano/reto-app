package com.pichicha.reto.app.api.controller.account;

import com.pichicha.reto.app.api.common.AbstractContainerBase;
import com.pichicha.reto.app.api.dto.common.IdCriteriaDTO;
import com.pichicha.reto.app.api.model.Account;
import com.pichicha.reto.app.api.utils.ControllerUtil;
import com.pichicha.reto.app.api.utils.enums.EnumAccountType;
import com.pichicha.reto.app.api.utils.enums.EnumStatus;
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
class AccountControllerTest extends AbstractContainerBase {

    private static final Long ACCOUNT_NUMBER = 478758L;
    public static final String CUSTOMER_ID = "1234567890";

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    @DisplayName("Find by ID")
    void givenAccountData_whenFindById_thenReturnAccountObject() {
        IdCriteriaDTO idCriteriaDTO = new IdCriteriaDTO(ACCOUNT_NUMBER);
        this.webTestClient
                .post()
                .uri(ControllerUtil.ACCOUNT_PATH.concat(ControllerUtil.FIND_PATH))
                .bodyValue(idCriteriaDTO)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Account.class)
                .value(account -> {
                    assertThat(account.getEstado()).isEqualTo(EnumStatus.ACT);
                    assertThat(account.getTipo()).isEqualTo(EnumAccountType.AHO);
                    assertThat(account.getSaldo()).isEqualTo(2000);
                    assertThat(account.getClienteId()).isEqualTo(CUSTOMER_ID);
                });
    }
}
