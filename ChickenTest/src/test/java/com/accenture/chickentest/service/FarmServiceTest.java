package com.accenture.chickentest.service;


import com.accenture.chickentest.domain.dao.Farm;


import com.accenture.chickentest.domain.dao.Transaction;
import com.accenture.chickentest.domain.dto.FarmDTO;

import com.accenture.chickentest.repository.FarmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FarmServiceTest {


    @Mock
    private FarmRepository farmRepository;

    @InjectMocks
    private FarmService farmService;


    private FarmDTO farmDTO;
    private FarmDTO farmDTO2;
    private Farm farm;
    private Farm farm2;
    private List<FarmDTO> listfarmsDTO;
    private List<Farm> listfarms;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        farm = new Farm();
        farm.setId(1L);
        farm.setEstimate(10000);
        farm.setName("Granja Las LEÑAS");

        farm2 = new Farm();
        farm2.setId(2L);
        farm2.setEstimate(40000);
        farm2.setName("Granja Los Pollos");

        farmDTO = new FarmDTO();
        farmDTO.setId(1L);
        farmDTO.setEstimate(10000);
        farmDTO.setName("Granja Las LEÑAS");
        farmDTO2 = new FarmDTO();
        farmDTO2.setId(2L);
        farmDTO2.setEstimate(40000);
        farmDTO2.setName("Granja Los Pollos");

        listfarmsDTO = new ArrayList<>();
        listfarmsDTO.add(farmDTO);
        listfarmsDTO.add(farmDTO2);

        listfarms = new ArrayList<>();
        listfarms.add(farm);
        listfarms.add(farm2);

    }

    @Test
    void save() {
        ResponseEntity<FarmDTO> responseEntity = ResponseEntity.of(Optional.empty());

        when(farmRepository.save(any(Farm.class))).thenReturn(farm2);

        responseEntity=farmService.save(farmDTO);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void update() {
        ResponseEntity<FarmDTO> responseEntity = ResponseEntity.of(Optional.empty());
        when(farmRepository.findById(farm.getId())).thenReturn(Optional.of(farm));
        farmDTO.setEstimate(30000);
        farmService.save(farmDTO);
        responseEntity = farmService.update(farmDTO.getId(),farmDTO);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(30000,farmDTO.getEstimate());

    }
    @Test
    void getFarm() {


        farm.setEstimate(100000);
        when(farmRepository.findById(farm.getId())).thenReturn(Optional.of(farm));
        String  name=farmService.getFarm(farm.getId()).getName();
        assertEquals("Granja Las LEÑAS",name);
    }


    @Test
    void deleteFarm() {
        ResponseEntity<FarmDTO> responseEntity = ResponseEntity.of(Optional.empty());
        when(farmRepository.findById(farm.getId())).thenReturn(of(farm));
        responseEntity = farmService.deleteFarm(1);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    void getFarms() {
        when(farmRepository.findAll()).thenReturn(listfarms);
        assertEquals(2,farmService.getFarms().stream().count());

    }

    @Test
    void getFarmIdByName() {
        when(farmRepository.findByName("Granja Las LEÑAS")).thenReturn(farm);

        FarmDTO farmDTO1=new FarmDTO();
        farmDTO1=  farmService.getFarmIdByName("Granja Las LEÑAS");
        assertEquals("Granja Las LEÑAS",farmDTO1.getName());
    }


}