package com.pichicha.reto.app.api.controller;

import com.pichicha.reto.app.api.model.Cliente;
import com.pichicha.reto.app.api.services.ClienteService;
import com.pichicha.reto.app.api.utils.AppUtils;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(AppUtils.CLIENTES_PATH)
public class ClienteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClienteController.class);

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Cliente> crear(@RequestBody @Valid Cliente nuevoCliente) {
        LOGGER.debug("crear(): {}", nuevoCliente);
        return this.clienteService.crear(nuevoCliente);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Cliente> actualizar(@PathVariable("id") String identificacion,
                                    @RequestBody @Valid Cliente updatedTask) {
        LOGGER.debug("actualizar(): {}", updatedTask);
        return this.clienteService.actualizar(identificacion, updatedTask);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> eliminar(@PathVariable("id") String identificacion) {
        LOGGER.debug("eliminar(): {}", identificacion);
        return this.clienteService.eliminar(identificacion);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Cliente> buscarPorId(@PathVariable("id") String id) {
        LOGGER.debug("buscarPorId() - START: {}", id);
        return this.clienteService.buscarPorId(id);
    }

}
