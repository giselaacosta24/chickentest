package com.accenture.chickentest.service;

import com.accenture.chickentest.domain.dao.Egg;
import com.accenture.chickentest.domain.dao.Parametro;
import com.accenture.chickentest.domain.dto.EggDTO;
import com.accenture.chickentest.domain.dto.ParametroDTO;
import com.accenture.chickentest.domain.enumStatus.Status;
import com.accenture.chickentest.mapper.ModelMapper;
import com.accenture.chickentest.repository.EggRepository;
import com.accenture.chickentest.repository.ParametroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class EggServiceTest {


    @Mock
    private EggRepository eggRepository;

    @InjectMocks
    private EggService eggService;

    private ParametroRepository parametroRepository;

    private  ParametroService parametroService;
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

        eggDTO.setStatus(Status.PUESTO);
        eggDTO.setDateFarm(new Date());
        List<ParametroDTO> parametros = new ArrayList<>();
        ParametroDTO parametro=new ParametroDTO();
        parametro.setId(1L);
        parametro.setClave("PrecioHuevos");
        parametro.setValor(15L);
        parametros.add(parametro);
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