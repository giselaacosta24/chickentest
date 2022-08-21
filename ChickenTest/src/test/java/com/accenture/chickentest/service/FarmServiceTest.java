package com.accenture.chickentest.service;


import com.accenture.chickentest.domain.dao.Farm;

import com.accenture.chickentest.domain.dto.FarmDTO;

import com.accenture.chickentest.repository.FarmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FarmServiceTest {


    @Mock
    private FarmRepository farmRepository;

    @InjectMocks
    private FarmService farmService;


    private FarmDTO farmDTO;
    private Farm farm;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        farm = new Farm();

        farm.setEstimate(10000);
        farm.setName("Granja Las LEÑAS");


    }

    @Test
    void save() {
        when(farmRepository.save(any(Farm.class))).thenReturn(farm);
        assertNotNull(farmService.save(new FarmDTO()));
    }

    @Test
    void getFarm() {
        farm= new Farm();
        farm.setId(1L);

        farm.setEstimate(100000);

        when(farmRepository.findById(farm.getId())).thenReturn(Optional.of(farm));

            assertNotNull(farmService.getFarm(farm.getId()));

    }

}