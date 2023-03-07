package com.pichicha.reto.app.api.services;

import com.pichicha.reto.app.api.exception.ResourceNotFoundException;
import com.pichicha.reto.app.api.model.Cuenta;
import com.pichicha.reto.app.api.repository.CuentaRepository;
import com.pichicha.reto.app.api.utils.enums.NotFoundErrorEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;

    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    public Mono<Cuenta> buscarPorId(long id) {
        return Mono.fromSupplier(() -> this.cuentaRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(NotFoundErrorEnum.CUENTA_NO_ENCONTRADA, id)))
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Cuenta> crear(Cuenta cuenta) {
        return Mono.just(cuenta)
                .map(this.cuentaRepository::save);
    }

    public Mono<Cuenta> actualizar(Long id, Cuenta cuentaActualizada) {
        return this.buscarPorId(id)
                .doOnNext(cuentaGuardada -> cuentaActualizada.setSaldo(cuentaGuardada.getSaldo()))
                .doOnNext(cuentaGuardada -> BeanUtils.copyProperties(cuentaActualizada, cuentaGuardada))
                .map(this.cuentaRepository::save);
    }

    public Cuenta actualizarSaldo(Long id, Double nuevoSaldo) {
        Cuenta cuentaGuardada = this.cuentaRepository.findById(id).orElse(null);
        if (cuentaGuardada == null) {
            throw new ResourceNotFoundException(NotFoundErrorEnum.CUENTA_NO_ENCONTRADA, id);
        }
        cuentaGuardada.setSaldo(nuevoSaldo);
        return this.cuentaRepository.save(cuentaGuardada);
    }

    public Mono<Void> eliminar(long cuentaId) {
        return this.buscarPorId(cuentaId)
                .doOnNext(this.cuentaRepository::delete)
                .then();
    }

    public Flux<Cuenta> buscarPorCliente(String clienteId) {
        return Flux.fromIterable(this.cuentaRepository.findByClienteId(clienteId))
                .subscribeOn(Schedulers.boundedElastic());
    }
}
