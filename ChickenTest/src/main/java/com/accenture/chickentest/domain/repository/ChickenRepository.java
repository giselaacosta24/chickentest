package com.accenture.chickentest.domain.repository;

import com.accenture.chickentest.domain.model.Chicken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChickenRepository extends JpaRepository<Chicken, Long> {

}
