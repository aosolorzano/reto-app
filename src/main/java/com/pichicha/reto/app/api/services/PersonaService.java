package com.pichicha.reto.app.api.services;

import com.pichicha.reto.app.api.exception.ResourceNotFoundException;
import com.pichicha.reto.app.api.model.Persona;
import com.pichicha.reto.app.api.repository.PersonaRepository;
import com.pichicha.reto.app.api.utils.enums.NotFoundErrorEnum;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class PersonaService {

    private final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    public Mono<Persona> buscarPorIdentificacion(String id) {
        return Mono.fromSupplier(() -> this.personaRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(NotFoundErrorEnum.PERSONA_NO_ENCONTRADA, id)))
                .subscribeOn(Schedulers.boundedElastic());
    }
}
