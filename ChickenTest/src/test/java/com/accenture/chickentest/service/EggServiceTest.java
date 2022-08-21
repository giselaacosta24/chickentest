package com.accenture.chickentest.service;

import com.accenture.chickentest.domain.dao.Egg;
import com.accenture.chickentest.domain.dto.EggDTO;
import com.accenture.chickentest.domain.enumStatus.Status;
import com.accenture.chickentest.repository.EggRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class EggServiceTest {


    @Mock
    private EggRepository eggRepository;

    @InjectMocks
    private EggService eggService;


    private EggDTO eggDTO;
    private Egg egg;




    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);

        egg= new Egg();
        egg.setId(1L);

        egg.setPrice(10);
        egg.setStatus(Status.PUESTO);
        egg.setDateFarm(new Date());

        eggDTO= new EggDTO();
        eggDTO.setId(1L);

        eggDTO.setPrice(10);
        eggDTO.setStatus(Status.PUESTO);
        eggDTO.setDateFarm(new Date());

    }

    @Test
    void save() {
        when(eggRepository.save(any(Egg.class))).thenReturn(egg);
        assertNotNull(eggService.save(new EggDTO()));
    }



    @Test
    void update(){


        eggDTO= new EggDTO();
        eggDTO.setId(1L);

        eggDTO.setPrice(10);
        eggDTO.setStatus(Status.PUESTO);
        eggDTO.setDateFarm(new Date());
        when(eggRepository.findById(egg.getId())).thenReturn(Optional.of(egg));

        assertNotNull(eggService.update(1L, eggDTO));    }
}