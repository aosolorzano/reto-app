package com.pichicha.reto.app.api.repository;

import com.pichicha.reto.app.api.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByClienteId(String clienteId);
}
