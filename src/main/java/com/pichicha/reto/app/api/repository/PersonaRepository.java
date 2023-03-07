package com.pichicha.reto.app.api.repository;

import com.pichicha.reto.app.api.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, String> {

}
