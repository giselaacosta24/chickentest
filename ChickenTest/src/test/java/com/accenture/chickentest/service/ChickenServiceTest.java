package com.accenture.chickentest.service;

import com.accenture.chickentest.domain.dao.Chicken;
import com.accenture.chickentest.domain.dto.ChickenDTO;
import com.accenture.chickentest.repository.ChickenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ChickenServiceTest {


    @Mock
    private  ChickenRepository chickenRepository;

    @InjectMocks
    private ChickenService chickenService;


    private Chicken chicken;




    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);

        chicken= new Chicken();
        chicken.setPrice(10);
        chicken.setSexo(false);
        chicken.setDateFarm(new Date());

    }

  /*  @Test
    void save() {
        when(chickenRepository.save(any(Chicken.class))).thenReturn(chicken);
        assertNotNull(chickenService.save(new ChickenDTO()));
    }*/



    @Test
    void getChickens(){

        assertNotNull(chickenService.getChickens());
    }
}