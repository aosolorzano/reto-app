package com.pichicha.reto.app.api.dao;

import com.pichicha.reto.app.api.dto.customer.CustomerCriteriaDTO;
import com.pichicha.reto.app.api.dto.customer.CustomerDTO;
import com.pichicha.reto.app.api.exception.ValidationException;
import com.pichicha.reto.app.api.model.Customer;
import com.pichicha.reto.app.api.utils.enums.EnumValidationError;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class CustomerDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDAO.class);

    private final EntityManager entityManager;

    public CustomerDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<CustomerDTO> find(CustomerCriteriaDTO customerCriteriaDTO) {
        LOGGER.debug("find() - START: {}", customerCriteriaDTO);
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<CustomerDTO> cq = cb.createQuery(CustomerDTO.class);
        Root<Customer> customerRoot = cq.from(Customer.class);
        cq.select(getCustomerConstruct(cb, customerRoot));

        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(customerCriteriaDTO.getId()) && !customerCriteriaDTO.getId().isEmpty()) {
            predicates.add(cb.like(customerRoot.get("id"), customerCriteriaDTO.getId() + "%"));
        }
        if (Objects.nonNull(customerCriteriaDTO.getName()) && !customerCriteriaDTO.getName().isEmpty()) {
            predicates.add(cb.like(customerRoot.get("nombre"), "%" + customerCriteriaDTO.getName() + "%"));
        }
        assignPredicates(cq, predicates);
        return this.entityManager.createQuery(cq).getResultList();
    }

    private static void assignPredicates(CriteriaQuery<CustomerDTO> cq, List<Predicate> predicates) {
        if (predicates.isEmpty()) {
            throw new ValidationException(EnumValidationError.NO_CRITERIA_FOUND);
        } else if (predicates.size() == 1) {
            cq.where(predicates.get(0));
        } else {
            cq.where(predicates.toArray(new Predicate[0]));
        }
    }

    private static CompoundSelection<CustomerDTO> getCustomerConstruct(CriteriaBuilder cb, Root<Customer> customerRoot) {
        return cb.construct(CustomerDTO.class, customerRoot.get("id"), customerRoot.get("nombre"),
                customerRoot.get("genero"), customerRoot.get("edad"), customerRoot.get("direccion"),
                customerRoot.get("telefono"));
    }
}
