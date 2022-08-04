package com.accenture.chickentest.repository;

import com.accenture.chickentest.domain.dao.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FarmRepository extends JpaRepository<Farm, Long> {
    @Query("SELECT f FROM Farm f WHERE f.name = :name")
    Farm findByName(@Param("name") String name);


}
