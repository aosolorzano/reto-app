package com.pichicha.reto.app.api.service;

import com.pichicha.reto.app.api.common.AbstractContainerBase;
import com.pichicha.reto.app.api.exception.ResourceNotFoundException;
import com.pichicha.reto.app.api.model.Cliente;
import com.pichicha.reto.app.api.services.ClienteService;
import com.pichicha.reto.app.api.utils.AppUtils;
import com.pichicha.reto.app.api.utils.enums.EstadoEnum;
import com.pichicha.reto.app.api.utils.enums.GeneroEnum;
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
class ClienteServiceTest extends AbstractContainerBase {

    private static final String CLIENTE_ID = "4561237890";

    private static Cliente cliente;

    @Autowired
    ClienteService clienteService;

    @BeforeAll
    public static void init() {
        cliente = AppUtils.getClienteTemplateObject();
        cliente.setId(CLIENTE_ID);
    }

    @Test
    @Order(1)
    @DisplayName("Crea cliente")
    void givenClientData_whenSave_thenSaveClientObject() {
        Mono<Cliente> clienteGuardado = this.clienteService.crear(cliente);
        StepVerifier.create(clienteGuardado)
                .assertNext(clienteResult -> {
                    assertThat(clienteResult.getId()).isEqualTo(CLIENTE_ID);
                    assertThat(clienteResult.getNombre()).isEqualTo("Andres Solorzano");
                    assertThat(clienteResult.getGenero()).isEqualTo(GeneroEnum.M);
                    assertThat(clienteResult.getEdad()).isEqualTo(37);
                    assertThat(clienteResult.getDireccion()).isEqualTo("Calle 1234");
                    assertThat(clienteResult.getTelefono()).isEqualTo("0987654321");
                    assertThat(clienteResult.getContrasenia()).isEqualTo("andres123");
                    assertThat(clienteResult.getEstado()).isEqualTo(EstadoEnum.ACT);
                })
                .verifyComplete();
    }

    @Test
    @Order(2)
    @DisplayName("Busca cliente por ID")
    void givenClientData_whenFindById_thenReturnClientObject() {
        Mono<Cliente> cliente = this.clienteService.buscarPorId(CLIENTE_ID);
        StepVerifier.create(cliente)
                .assertNext(clienteResult -> {
                    assertThat(clienteResult.getId()).isEqualTo(CLIENTE_ID);
                    assertThat(clienteResult.getNombre()).isEqualTo("Andres Solorzano");
                    assertThat(clienteResult.getGenero()).isEqualTo(GeneroEnum.M);
                    assertThat(clienteResult.getEdad()).isEqualTo(37);
                    assertThat(clienteResult.getDireccion()).isEqualTo("Calle 1234");
                    assertThat(clienteResult.getTelefono()).isEqualTo("0987654321");
                    assertThat(clienteResult.getContrasenia()).isEqualTo("andres123");
                    assertThat(clienteResult.getEstado()).isEqualTo(EstadoEnum.ACT);
                })
                .verifyComplete();
    }

    @Test
    @Order(3)
    @DisplayName("Modifica cliente")
    void givenClientData_whenModifyData_thenSaveClientObject() {
        cliente.setNombre("Nombres modificados");
        cliente.setTelefono("1234567890");
        cliente.setDireccion("Calle actualizada");
        Mono<Cliente> clienteModificado = this.clienteService.actualizar(CLIENTE_ID, cliente);
        StepVerifier.create(clienteModificado)
                .assertNext(clienteResult -> {
                    assertThat(clienteResult.getId()).isEqualTo(CLIENTE_ID);
                    assertThat(clienteResult.getNombre()).isEqualTo("Nombres modificados");
                    assertThat(clienteResult.getGenero()).isEqualTo(GeneroEnum.M);
                    assertThat(clienteResult.getEdad()).isEqualTo(37);
                    assertThat(clienteResult.getDireccion()).isEqualTo("Calle actualizada");
                    assertThat(clienteResult.getTelefono()).isEqualTo("1234567890");
                    assertThat(clienteResult.getContrasenia()).isEqualTo("andres123");
                    assertThat(clienteResult.getEstado()).isEqualTo(EstadoEnum.ACT);
                })
                .verifyComplete();
    }

    @Test
    @Order(4)
    @DisplayName("Elimina cliente por ID")
    void givenClientData_whenDeleteById_thenDeleteClientObject() {
        Mono<Void> clienteEliminado = this.clienteService.eliminar(CLIENTE_ID);
        StepVerifier.create(clienteEliminado)
                .verifyComplete();
    }

    @Test
    @Order(5)
    @DisplayName("Busca cliente eliminado por ID")
    void givenDeletedClientData_whenFindById_thenReturn404() {
        Mono<Cliente> cliente = this.clienteService.buscarPorId(CLIENTE_ID);
        StepVerifier.create(cliente)
                .expectError(ResourceNotFoundException.class)
                .verify();
    }
}
