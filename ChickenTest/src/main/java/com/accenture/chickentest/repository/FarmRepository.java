package com.accenture.chickentest.repository;

import com.accenture.chickentest.domain.dao.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmRepository extends JpaRepository<Farm, Long> {
}
