package com.accenture.chickentest.repository;

import com.accenture.chickentest.domain.dao.Farm;
import com.accenture.chickentest.domain.dao.Parametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ParametroRepository  extends JpaRepository<Parametro, Long> {

    @Query("SELECT p FROM Parametro p WHERE p.clave = :clave")
    Optional<Parametro> findByName(@Param("clave") String clave);
}
