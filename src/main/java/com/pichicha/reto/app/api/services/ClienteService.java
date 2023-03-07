package com.pichicha.reto.app.api.services;

import com.pichicha.reto.app.api.exception.ResourceNotFoundException;
import com.pichicha.reto.app.api.model.Cliente;
import com.pichicha.reto.app.api.repository.ClienteRepository;
import com.pichicha.reto.app.api.utils.enums.NotFoundErrorEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Mono<Cliente> crear(Cliente cliente) {
        return Mono.just(cliente)
                .map(this.clienteRepository::save);
    }

    public Mono<Cliente> actualizar(String identificacion, Cliente clienteModificado) {
        return this.buscarPorId(identificacion)
                .doOnNext(clienteGuardado -> BeanUtils.copyProperties(clienteModificado, clienteGuardado))
                .map(this.clienteRepository::save);
    }

    public Mono<Void> eliminar(String identificacion) {
        return this.buscarPorId(identificacion)
                .doOnNext(this.clienteRepository::delete)
                .then();
    }

    public Mono<Cliente> buscarPorId(String id) {
        return Mono.fromSupplier(() -> this.clienteRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(NotFoundErrorEnum.CLIENTE_NO_ENCONTRADO, id)))
                .subscribeOn(Schedulers.boundedElastic());
    }
}
