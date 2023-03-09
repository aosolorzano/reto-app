package com.pichicha.reto.app.api.controller;

import com.pichicha.reto.app.api.dto.CustomerDTO;
import com.pichicha.reto.app.api.services.CustomerService;
import com.pichicha.reto.app.api.utils.ControllerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(ControllerUtil.CUSTOMERS_PATH)
public class CustomersController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomersController.class);

    private final CustomerService customerService;

    public CustomersController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public Flux<CustomerDTO> findAll() {
        LOGGER.debug("findAll() - START");
        return Flux.empty();
    }
}
