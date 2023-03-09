package com.pichicha.reto.app.api.services;

import com.pichicha.reto.app.api.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    public void sendCreationEmail(Customer customer) {
        LOGGER.debug("enviarCorreoActivacion() - START: {}", customer);
    }

    public void sendModificationEmail(Customer customer) {
        LOGGER.debug("enviarCorreoActualizacion() - START: {}", customer);
    }
}
