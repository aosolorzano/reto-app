package com.pichicha.reto.app.api.services;

import com.pichicha.reto.app.api.exception.MovimientoException;
import com.pichicha.reto.app.api.exception.ResourceNotFoundException;
import com.pichicha.reto.app.api.model.Cuenta;
import com.pichicha.reto.app.api.model.Movimiento;
import com.pichicha.reto.app.api.repository.CuentaRepository;
import com.pichicha.reto.app.api.repository.MovimientoRepository;
import com.pichicha.reto.app.api.utils.enums.EstadoEnum;
import com.pichicha.reto.app.api.utils.enums.MovimientoErrorEnum;
import com.pichicha.reto.app.api.utils.enums.NotFoundErrorEnum;
import com.pichicha.reto.app.api.utils.enums.TipoMovimientoEnum;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.ZonedDateTime;

@Service
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;

    private final CuentaRepository cuentaRepository;

    private final CuentaService cuentaService;

    public MovimientoService(MovimientoRepository movimientoRepository, CuentaService cuentaService,
                             CuentaRepository cuentaRepository) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaService = cuentaService;
        this.cuentaRepository = cuentaRepository;
    }

    public Mono<Movimiento> buscarPorId(long id) {
        return Mono.fromSupplier(() -> this.movimientoRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(NotFoundErrorEnum.CUENTA_NO_ENCONTRADA, id)))
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Movimiento> crear(Movimiento nuevoMovimiento) {
        return this.cuentaService.buscarPorId(nuevoMovimiento.getNumeroCuenta())
                .publishOn(Schedulers.boundedElastic())
                .map(cuenta -> {
                    double saldoCuenta = cuenta.getSaldo();
                    nuevoMovimiento.setSaldoInicial(saldoCuenta);
                    if (nuevoMovimiento.getTipo().equals(TipoMovimientoEnum.RET)) {
                        saldoCuenta -= nuevoMovimiento.getValor();
                    } else if (nuevoMovimiento.getTipo().equals(TipoMovimientoEnum.DEP)) {
                        saldoCuenta += nuevoMovimiento.getValor();
                    }
                    this.validarSaldoFinal(nuevoMovimiento, saldoCuenta);
                    return this.cuentaService.actualizarSaldo(cuenta.getNumeroCuenta(), saldoCuenta);
                })
                .publishOn(Schedulers.boundedElastic())
                .map(cuenta -> {
                    nuevoMovimiento.setFecha(ZonedDateTime.now());
                    nuevoMovimiento.setEstado(EstadoEnum.ACT);
                    return this.movimientoRepository.save(nuevoMovimiento);
                });
    }

    public Mono<Void> eliminar(Long id) {
        return this.buscarPorId(id)
                .publishOn(Schedulers.boundedElastic())
                .map(movimiento -> {
                    this.movimientoRepository.delete(movimiento);
                    return movimiento;
                })
                .doOnNext(movimientoExistente -> {
                    Cuenta cuenta = this.validarCuenta(movimientoExistente);
                    double saldoCuenta = cuenta.getSaldo();
                    if (movimientoExistente.getTipo().equals(TipoMovimientoEnum.RET)) {
                        saldoCuenta += movimientoExistente.getValor();
                    } else if (movimientoExistente.getTipo().equals(TipoMovimientoEnum.DEP)) {
                        saldoCuenta -= movimientoExistente.getValor();
                    }
                    this.validarSaldoFinal(movimientoExistente, saldoCuenta);
                    this.cuentaService.actualizarSaldo(movimientoExistente.getNumeroCuenta(),
                            saldoCuenta);
                })
                .then();
    }

    private void validarSaldoFinal(Movimiento movimientoExistente, double saldoCuenta) {
        if (saldoCuenta < 0) {
            movimientoExistente.setFecha(ZonedDateTime.now());
            movimientoExistente.setEstado(EstadoEnum.INA);
            this.movimientoRepository.save(movimientoExistente);
            throw new MovimientoException(MovimientoErrorEnum.SALDO_INSUFICIENTE,
                    movimientoExistente.getNumeroCuenta());
        }
    }

    private Cuenta validarCuenta(Movimiento movimientoExistente) {
        Cuenta cuenta = this.cuentaRepository.findById(movimientoExistente.getNumeroCuenta()).orElse(null);
        if (cuenta == null) {
            throw new ResourceNotFoundException(NotFoundErrorEnum.CUENTA_NO_ENCONTRADA,
                    movimientoExistente.getNumeroCuenta());
        }
        return cuenta;
    }
}
