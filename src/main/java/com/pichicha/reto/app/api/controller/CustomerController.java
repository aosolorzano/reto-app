package com.pichicha.reto.app.api.controller;

import com.pichicha.reto.app.api.dto.CustomerCriteriaDTO;
import com.pichicha.reto.app.api.dto.CustomerDTO;
import com.pichicha.reto.app.api.services.CustomerService;
import com.pichicha.reto.app.api.utils.ControllerUtil;
import com.pichicha.reto.app.api.utils.DataUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ControllerUtil.CUSTOMER_PATH)
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> create(@RequestBody @Valid Mono<CustomerDTO> customerDTO) {
        LOGGER.debug("create(): {}", customerDTO);
        return this.customerService.create(customerDTO);
    }

    @PostMapping(ControllerUtil.FIND_PATH)
    public Mono<ResponseEntity<CustomerDTO>> find(@RequestBody @Valid CustomerCriteriaDTO customerDTO) {
        LOGGER.debug("find() - START: {}", customerDTO);
        return this.customerService.findById(customerDTO.getId())
                .map(ResponseEntity::ok);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> update(@RequestBody @Valid CustomerDTO customerDTO) {
        LOGGER.debug("update(): {}", customerDTO);
        return this.customerService.update(customerDTO);
    }

    @PatchMapping("/changeState")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> changeState(@RequestBody @Valid CustomerDTO customerDTO) {
        LOGGER.debug("changeState(): {}", customerDTO);
        return this.customerService.update(customerDTO);
    }

    @PatchMapping("/changePassword")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> changePassword(@RequestBody @Valid CustomerDTO customerDTO) {
        LOGGER.debug("changePassword(): {}", customerDTO);
        return this.customerService.update(customerDTO);
    }

    @PatchMapping(ControllerUtil.DELETE_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@RequestBody @Valid CustomerDTO customerDTO) {
        LOGGER.debug("delete(): {}", customerDTO.getId());
        return this.customerService.delete(customerDTO.getId());
    }

    @GetMapping("/templateBody")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getTaskTemplate() {
        return DataUtil.getCustomerTemplateDTO();
    }
}
