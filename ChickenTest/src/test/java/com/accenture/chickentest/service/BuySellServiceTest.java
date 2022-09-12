package com.accenture.chickentest.service;

import com.accenture.chickentest.domain.dao.Chicken;
import com.accenture.chickentest.domain.dao.Egg;
import com.accenture.chickentest.domain.dao.Farm;
import com.accenture.chickentest.domain.dao.Parametro;
import com.accenture.chickentest.domain.dto.ChickenDTO;

import com.accenture.chickentest.domain.dto.EggDTO;
import com.accenture.chickentest.domain.dto.FarmDTO;
import com.accenture.chickentest.domain.dto.ParametroDTO;

import com.accenture.chickentest.repository.*;
import lombok.val;
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

import static org.mockito.Mockito.when;

class BuySellServiceTest {


    @Mock
    private  ChickenRepository chickenRepository;
    @Mock
    private  EggRepository eggRepository;
    @Mock
    private  TransactionRepository transactionRepository;
    @Mock
    private ParametroRepository parametroRepository;
    @Mock
    private FarmRepository farmRepository;
    @InjectMocks
    private BuySellService buySellService;
    @InjectMocks
    private FarmService farmService;

    @InjectMocks
    private ParametroService parametroService;
    private ChickenDTO chickenDTO;
    private EggDTO eggDTO;

    private Chicken chicken;
    private Egg egg;
    private FarmDTO farm;

    private  Long id;
    private Parametro parametro;

    private  ArrayList<ParametroDTO> parametrosList;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);

        buySellService = new BuySellService(chickenRepository,eggRepository,transactionRepository,parametroRepository,farmRepository,farmService,parametroService);

        chicken= new Chicken();
        chicken.setPrice(10);
        chicken.setSexo(false);
        chicken.setDateFarm(new Date());
        chicken.setId(1L);

        egg= new Egg();
        egg.setPrice(10);
        egg.setDateFarm(new Date());
        egg.setId(1L);

        parametro = new Parametro();
        parametro.setClave("CapacidadMaximaGranja");
        parametro.setId(1L);
        parametro.setValor(2L);


        chickenDTO= new ChickenDTO();
        chickenDTO.setId(1L);
        chickenDTO.setPrice(10);
        chickenDTO.setSexo(false);
        chickenDTO.setDateFarm(new Date());

        farm = new FarmDTO();
        farm.setId(8L);
        farm.setEstimate(1001);
        farm.setName("FarmTest");

        eggDTO= new EggDTO();
        eggDTO.setId(1L);
        eggDTO.setPrice(10);
        eggDTO.setDateFarm(new Date());

    }




    @Test
    void sellChicken() {
        ResponseEntity<ChickenDTO> responseEntity = ResponseEntity.of(Optional.empty());
        when(chickenRepository.findById(chicken.getId())).thenReturn(of(chicken));
        responseEntity=buySellService.sellChicken( chickenDTO.getId());
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    void buyChicken() {
        ResponseEntity<ChickenDTO> responseEntity = ResponseEntity.of(Optional.empty());
        when(farmRepository.findById(farm.getId())).thenReturn(Optional.<Farm>of(farm));
        when(parametroRepository.findByName("CapacidadMaximaGranja")).thenReturn(of(parametro));
        when(chickenRepository.findById(chicken.getId())).thenReturn(of(chicken));
        responseEntity = buySellService.buyChicken(chickenDTO, farm.getId());
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void buyEgg() {
        ResponseEntity<EggDTO> responseEntity = ResponseEntity.of(Optional.empty());
        eggDTO = new EggDTO();
        eggDTO.setId(8L);
        eggDTO.setPrice(10);
        eggDTO.setDateFarm(new Date());

        when(farmRepository.findById(farm.getId())).thenReturn(Optional.<Farm>of(farm));
        when(parametroRepository.findByName("CapacidadMaximaGranja")).thenReturn(of(parametro));
        when(eggRepository.findById(egg.getId())).thenReturn(of(egg));
        responseEntity = buySellService.buyEgg(eggDTO, farm.getId());
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }



    @Test
    void sellEgg() {

        ResponseEntity<EggDTO> responseEntity = ResponseEntity.of(Optional.empty());
        when(eggRepository.findById(egg.getId())).thenReturn(of(egg));
        responseEntity=buySellService.sellEgg(eggDTO.getId());
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}