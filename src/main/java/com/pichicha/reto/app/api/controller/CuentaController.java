package com.pichicha.reto.app.api.controller;

import com.pichicha.reto.app.api.model.Cuenta;
import com.pichicha.reto.app.api.services.CuentaService;
import com.pichicha.reto.app.api.utils.AppUtils;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(AppUtils.CUENTAS_PATH)
public class CuentaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CuentaController.class);

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Cuenta> crear(@RequestBody @Valid Cuenta nuevoCuenta) {
        LOGGER.debug("crear(): {}", nuevoCuenta);
        return this.cuentaService.crear(nuevoCuenta);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Cuenta> actualizar(@PathVariable("id") Long id,
                                    @RequestBody @Valid Cuenta cuentaActualizada) {
        LOGGER.debug("actualizar(): {}", cuentaActualizada);
        return this.cuentaService.actualizar(id, cuentaActualizada);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> eliminar(@PathVariable("id") Long id) {
        LOGGER.debug("eliminar(): {}", id);
        return this.cuentaService.eliminar(id);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Cuenta> buscarPorId(@PathVariable("id") Long id) {
        LOGGER.debug("buscarPorId() - START: {}", id);
        return this.cuentaService.buscarPorId(id);
    }

}
