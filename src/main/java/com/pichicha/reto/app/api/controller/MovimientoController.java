package com.pichicha.reto.app.api.controller;

import com.pichicha.reto.app.api.model.Movimiento;
import com.pichicha.reto.app.api.services.MovimientoService;
import com.pichicha.reto.app.api.utils.AppUtils;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(AppUtils.MOVIMIENTOS_PATH)
public class MovimientoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovimientoController.class);

    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Movimiento> crear(@RequestBody @Valid Movimiento nuevoMovimiento) {
        LOGGER.debug("crear(): {}", nuevoMovimiento);
        return this.movimientoService.crear(nuevoMovimiento);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> eliminar(@PathVariable("id") Long id) {
        LOGGER.debug("eliminar(): {}", id);
        return this.movimientoService.eliminar(id);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Movimiento> buscarPorId(@PathVariable("id") Long id) {
        LOGGER.debug("buscarPorId() - START: {}", id);
        return this.movimientoService.buscarPorId(id);
    }
}
