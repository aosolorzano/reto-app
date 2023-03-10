package com.pichicha.reto.app.api.controller.customer;

import com.pichicha.reto.app.api.dto.customer.CustomerCriteriaDTO;
import com.pichicha.reto.app.api.dto.customer.CustomerDTO;
import com.pichicha.reto.app.api.service.CustomerService;
import com.pichicha.reto.app.api.utils.ControllerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping
    public Flux<CustomerDTO> find(@RequestBody CustomerCriteriaDTO customerDTO) {
        LOGGER.debug("find() - START: {}", customerDTO);
        return this.customerService.find(customerDTO);
    }
}
