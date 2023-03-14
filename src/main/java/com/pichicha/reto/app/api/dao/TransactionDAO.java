package com.pichicha.reto.app.api.dao;

import com.pichicha.reto.app.api.dto.transaction.TransactionCriteriaDTO;
import com.pichicha.reto.app.api.dto.transaction.TransactionDTO;
import com.pichicha.reto.app.api.exception.ValidationException;
import com.pichicha.reto.app.api.model.Transaction;
import com.pichicha.reto.app.api.utils.DateUtil;
import com.pichicha.reto.app.api.utils.enums.EnumValidationError;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class TransactionDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionDAO.class);

    private final EntityManager entityManager;

    @Value("${reto.time.zone.id:-05:00}")
    private String zoneId;

    public TransactionDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<TransactionDTO> find(TransactionCriteriaDTO transactionCriteriaDTO) {
        LOGGER.debug("find() - START: {}", transactionCriteriaDTO);
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<TransactionDTO> cq = cb.createQuery(TransactionDTO.class);
        Root<Transaction> transactionRoot = cq.from(Transaction.class);
        cq.select(getTransactionConstruct(cb, transactionRoot));

        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(transactionCriteriaDTO.getAccountNumber())) {
            predicates.add(cb.equal(transactionRoot.get("accountNumber"), transactionCriteriaDTO.getAccountNumber()));
        }
        if (Objects.nonNull(transactionCriteriaDTO.getFromDate())) {
            if (Objects.isNull(transactionCriteriaDTO.getToDate())) {
                predicates.add(cb.greaterThanOrEqualTo(transactionRoot.get("date"),
                        DateUtil.getWithFirstTimeOfDay(transactionCriteriaDTO.getFromDate(), this.zoneId)));
            } else {
                predicates.add(cb.between(transactionRoot.get("date"),
                        DateUtil.getWithFirstTimeOfDay(transactionCriteriaDTO.getFromDate(), this.zoneId),
                        DateUtil.getWithLastTimeOfDay(transactionCriteriaDTO.getToDate(), this.zoneId)));
            }
        }
        assignPredicates(cq, predicates);
        return this.entityManager.createQuery(cq).getResultList();
    }

    private static void assignPredicates(CriteriaQuery<TransactionDTO> cq, List<Predicate> predicates) {
        if (predicates.isEmpty()) {
            throw new ValidationException(EnumValidationError.NO_CRITERIA_FOUND);
        } else if (predicates.size() == 1) {
            cq.where(predicates.get(0));
        } else {
            cq.where(predicates.toArray(new Predicate[0]));
        }
    }

    private static CompoundSelection<TransactionDTO> getTransactionConstruct(CriteriaBuilder cb, Root<Transaction> transactionRoot) {
        return cb.construct(TransactionDTO.class, transactionRoot.get("id"), transactionRoot.get("date"),
                transactionRoot.get("accountNumber"), transactionRoot.get("type"), transactionRoot.get("status"),
                transactionRoot.get("value"));
    }
}
