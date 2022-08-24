package com.accenture.chickentest.service;

import com.accenture.chickentest.domain.dao.Chicken;
import com.accenture.chickentest.domain.dto.ChickenDTO;
import com.accenture.chickentest.repository.ChickenRepository;
import com.accenture.chickentest.repository.EggRepository;
import com.accenture.chickentest.repository.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class BuySellServiceTest {


    @Mock
    private  ChickenRepository chickenRepository;
    @Mock
    private  EggRepository eggRepository;
    @Mock
    private  TransactionRepository transactionRepository;

    @InjectMocks
    private BuySellService buySellService;


    private ChickenDTO chickenDTO;

    private Chicken chicken;
     private  Long id;



    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        chicken= new Chicken();
        chicken.setPrice(10);
        chicken.setSexo(false);
        chicken.setDateFarm(new Date());
        chicken.setId(1L);


        id= Long.valueOf(1);


    }




    @Test
    void sellChicken() {
        chickenDTO= new ChickenDTO();
        chickenDTO.setId(1L);

        chickenDTO.setPrice(10);
        chickenDTO.setSexo(false);
        chickenDTO.setDateFarm(new Date());

        when(chickenRepository.findById(chicken.getId())).thenReturn(Optional.of(chicken));

        assertNotNull(buySellService.sellChicken( chickenDTO.getId()));
    }
}