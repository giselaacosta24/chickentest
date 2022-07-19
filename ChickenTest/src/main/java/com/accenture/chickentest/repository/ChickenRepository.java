package com.accenture.chickentest.repository;

import com.accenture.chickentest.domain.dao.Chicken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChickenRepository extends JpaRepository<Chicken, Long> {

}
