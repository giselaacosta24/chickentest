package com.accenture.chickentest.repository;

import com.accenture.chickentest.domain.dao.Chicken;

import com.accenture.chickentest.domain.dto.ChickenDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;


public interface ChickenRepository extends JpaRepository<Chicken, Long> {


}
