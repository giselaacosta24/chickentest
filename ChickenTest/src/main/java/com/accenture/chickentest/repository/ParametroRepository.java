package com.accenture.chickentest.repository;

import com.accenture.chickentest.domain.dao.Parametro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParametroRepository  extends JpaRepository<Parametro, Long> {
}
