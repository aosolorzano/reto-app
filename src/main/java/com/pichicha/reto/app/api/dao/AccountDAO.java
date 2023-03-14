package com.pichicha.reto.app.api.dao;

import com.pichicha.reto.app.api.dto.account.AccountCriteriaDTO;
import com.pichicha.reto.app.api.exception.ValidationException;
import com.pichicha.reto.app.api.model.Account;
import com.pichicha.reto.app.api.utils.enums.EnumValidationError;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class AccountDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountDAO.class);

    private final EntityManager entityManager;

    public AccountDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Account> find(AccountCriteriaDTO accountCriteriaDTO) {
        LOGGER.debug("find() - START: {}", accountCriteriaDTO);
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> cq = cb.createQuery(Account.class);
        Root<Account> accountRoot = cq.from(Account.class);

        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(accountCriteriaDTO.getAccountNumber()) && accountCriteriaDTO.getAccountNumber() > 0L) {
            predicates.add(cb.equal(accountRoot.get("numeroCuenta"), accountCriteriaDTO.getAccountNumber()));
        }
        if (Objects.nonNull(accountCriteriaDTO.getCustomerId()) && !accountCriteriaDTO.getCustomerId().isEmpty()) {
            predicates.add(cb.like(accountRoot.get("clienteId"), accountCriteriaDTO.getCustomerId() + "%"));
        }
        assignPredicates(cq, predicates);
        return this.entityManager.createQuery(cq).getResultList();
    }

    private static void assignPredicates(CriteriaQuery<Account> cq, List<Predicate> predicates) {
        if (predicates.isEmpty()) {
            throw new ValidationException(EnumValidationError.NO_CRITERIA_FOUND);
        } else if (predicates.size() == 1) {
            cq.where(predicates.get(0));
        } else {
            cq.where(predicates.toArray(new Predicate[0]));
        }
    }
}
