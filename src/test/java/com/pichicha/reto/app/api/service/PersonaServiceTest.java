package com.pichicha.reto.app.api.service;

import com.pichicha.reto.app.api.common.AbstractContainerBase;
import com.pichicha.reto.app.api.model.Persona;
import com.pichicha.reto.app.api.services.PersonaService;
import com.pichicha.reto.app.api.utils.enums.EnumGenre;
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
class PersonaServiceTest extends AbstractContainerBase {

    private static final String PERSONA_ID = "1234567890";

    @Autowired
    PersonaService personaService;

    @Test
    @Order(1)
    @DisplayName("Find persona by ID")
    void givenPersonaData_whenFindById_thenReturnPersonaObject() {
        Mono<Persona> persona = this.personaService.findById(PERSONA_ID);
        StepVerifier.create(persona)
                .assertNext(personaResult -> {
                    assertThat(personaResult.getId()).isEqualTo(PERSONA_ID);
                    assertThat(personaResult.getNombre()).isEqualTo("Jose Lema");
                    assertThat(personaResult.getGenero()).isEqualTo(EnumGenre.M);
                    assertThat(personaResult.getEdad()).isEqualTo(32);
                    assertThat(personaResult.getDireccion()).isEqualTo("Otavalo sn y principal");
                    assertThat(personaResult.getTelefono()).isEqualTo("098254785");
                })
                .verifyComplete();
    }
}
