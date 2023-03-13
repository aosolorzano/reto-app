package com.pichicha.reto.app.api.controller.customer;

import com.pichicha.reto.app.api.dto.customer.CustomerDTO;
import com.pichicha.reto.app.api.dto.customer.CustomerPasswordDTO;
import com.pichicha.reto.app.api.dto.customer.CustomerStatusDTO;
import com.pichicha.reto.app.api.service.CustomerService;
import com.pichicha.reto.app.api.utils.ControllerUtil;
import com.pichicha.reto.app.api.utils.DataUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
    public Mono<CustomerDTO> create(@RequestBody @Valid Mono<CustomerDTO> customerDTO) {
        LOGGER.debug("create(): {}", customerDTO);
        return this.customerService.create(customerDTO);
    }

    @GetMapping(ControllerUtil.FIND_PATH + "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CustomerDTO> findById(@PathVariable("id") String customerId) {
        LOGGER.debug("findById() - START: {}", customerId);
        return this.customerService.findById(customerId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<CustomerDTO> update(@RequestBody @Valid CustomerDTO customerDTO) {
        LOGGER.debug("update(): {}", customerDTO);
        return this.customerService.update(customerDTO);
    }

    @PutMapping(ControllerUtil.STATUS_PATH)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> updateStatus(@RequestBody @Valid CustomerStatusDTO customerStatusDTO) {
        LOGGER.debug("updateStatus(): {}", customerStatusDTO);
        return this.customerService.updateStatus(customerStatusDTO);
    }

    @PutMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> updatePassword(@RequestBody @Valid CustomerPasswordDTO customerPasswordDTO) {
        LOGGER.debug("updatePassword(): {}", customerPasswordDTO);
        return this.customerService.updatePassword(customerPasswordDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable("id") String customerId) {
        LOGGER.debug("delete(): {}", customerId);
        return this.customerService.delete(customerId);
    }

    @GetMapping("/templateBody")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getTaskTemplate() {
        return DataUtil.getCustomerTemplateDTO();
    }
}
