package com.pichicha.reto.app.api.repository;

import com.pichicha.reto.app.api.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    List<Movimiento> findByNumeroCuenta(long numeroCuenta);
}
