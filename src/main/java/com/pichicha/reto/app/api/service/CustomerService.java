package com.pichicha.reto.app.api.service;

import com.pichicha.reto.app.api.dto.customer.CustomerCriteriaDTO;
import com.pichicha.reto.app.api.dto.customer.CustomerDTO;
import com.pichicha.reto.app.api.dto.customer.CustomerPasswordDTO;
import com.pichicha.reto.app.api.dto.customer.CustomerStatusDTO;
import com.pichicha.reto.app.api.exception.ResourceNotFoundException;
import com.pichicha.reto.app.api.model.Customer;
import com.pichicha.reto.app.api.repository.CustomerRepository;
import com.pichicha.reto.app.api.utils.EntityUtil;
import com.pichicha.reto.app.api.utils.PasswdUtil;
import com.pichicha.reto.app.api.utils.enums.EnumAppError;
import com.pichicha.reto.app.api.utils.enums.EnumStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final EmailService emailService;

    public CustomerService(CustomerRepository customerRepository, EmailService emailService) {
        this.customerRepository = customerRepository;
        this.emailService = emailService;
    }

    public Mono<CustomerDTO> findById(String id) {
        return Mono
                .fromSupplier(() ->
                        this.customerRepository.findById(id).orElseThrow(() ->
                                new ResourceNotFoundException(EnumAppError.CUSTOMER_NOT_FOUND, id)))
                .map(EntityUtil::toDto)
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Void> create(Mono<CustomerDTO> customer) {
        return customer
                .map(EntityUtil::toEntity)
                .doOnNext(customerEntity -> customerEntity.setClave(PasswdUtil.generatePassword()))
                .doOnNext(customerEntity -> customerEntity.setEstado(EnumStatus.SUS))
                .map(this.customerRepository::save)
                .doOnNext(this.emailService::sendCreationEmail)
                .then();
    }

    public Mono<Void> update(CustomerDTO updatedCustomer) {
        return this.findEntityById(updatedCustomer.getId())
                .doOnNext(actualCustomer -> BeanUtils.copyProperties(updatedCustomer, actualCustomer))
                .map(this.customerRepository::save)
                .doOnNext(this.emailService::sendModificationEmail)
                .then();
    }

    public Mono<Void> updateStatus(CustomerStatusDTO customerStatusDTO) {
        return this.findEntityById(customerStatusDTO.getId())
                .doOnNext(customer -> customer.setEstado(customerStatusDTO.getStatus()))
                .map(this.customerRepository::save)
                .then();
    }

    public Mono<Void> updatePassword(CustomerPasswordDTO customerPasswordDTO) {
        return this.findEntityById(customerPasswordDTO.getId())
                .doOnNext(customer -> customer.setClave(customerPasswordDTO.getPassword()))
                .map(this.customerRepository::save)
                .then();
    }

    public Mono<Void> delete(String customerId) {
        return this.findEntityById(customerId)
                .doOnNext(this.customerRepository::delete)
                .then();
    }

    private Mono<Customer> findEntityById(String customerId) {
        return Mono.fromSupplier(() ->
                this.customerRepository.findById(customerId).orElseThrow(() ->
                        new ResourceNotFoundException(EnumAppError.CUSTOMER_NOT_FOUND, customerId)))
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Flux<CustomerDTO> find(CustomerCriteriaDTO customerDTO) {
        return Flux.empty();
    }
}