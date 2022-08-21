package com.accenture.chickentest.repository;

import com.accenture.chickentest.domain.dao.Egg;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EggRepository extends JpaRepository<Egg, Long> {

}
