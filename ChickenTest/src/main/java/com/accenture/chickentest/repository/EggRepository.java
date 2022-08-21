package com.accenture.chickentest.repository;

import com.accenture.chickentest.domain.dao.Egg;
import com.accenture.chickentest.domain.dto.ChickenDTO;
import com.accenture.chickentest.domain.dto.EggDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EggRepository extends JpaRepository<Egg, Long> {

}
