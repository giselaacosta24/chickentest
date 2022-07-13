package com.accenture.chickentest.domain.repository;

import com.accenture.chickentest.domain.model.Egg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EggRepository extends JpaRepository<Egg, Long> {

}
