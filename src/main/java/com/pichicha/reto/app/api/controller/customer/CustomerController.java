package com.pichicha.reto.app.api.controller.customer;

import com.pichicha.reto.app.api.dto.customer.CustomerDTO;
import com.pichicha.reto.app.api.dto.customer.CustomerIdDTO;
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
    public Mono<Void> create(@RequestBody @Valid Mono<CustomerDTO> customerDTO) {
        LOGGER.debug("create(): {}", customerDTO);
        return this.customerService.create(customerDTO);
    }

    @PostMapping(ControllerUtil.FIND_PATH)
    @ResponseStatus(HttpStatus.OK)
    public Mono<CustomerDTO> findById(@RequestBody @Valid CustomerIdDTO customerIdDTO) {
        LOGGER.debug("findById() - START: {}", customerIdDTO);
        return this.customerService.findById(customerIdDTO.id());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> update(@RequestBody @Valid CustomerDTO customerDTO) {
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

    @PostMapping(ControllerUtil.DELETE_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@RequestBody @Valid CustomerIdDTO customerIdDTO) {
        LOGGER.debug("delete(): {}", customerIdDTO);
        return this.customerService.delete(customerIdDTO.id());
    }

    @GetMapping("/templateBody")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getTaskTemplate() {
        return DataUtil.getCustomerTemplateDTO();
    }
}
