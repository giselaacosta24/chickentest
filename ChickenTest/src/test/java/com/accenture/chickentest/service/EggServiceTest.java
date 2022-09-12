package com.accenture.chickentest.service;

import com.accenture.chickentest.domain.dao.Egg;

import com.accenture.chickentest.domain.dao.Parametro;
import com.accenture.chickentest.domain.dao.Transaction;
import com.accenture.chickentest.domain.dto.ChickenDTO;
import com.accenture.chickentest.domain.dto.EggDTO;
import com.accenture.chickentest.domain.dto.ParametroDTO;
import com.accenture.chickentest.domain.enumStatus.Status;

import com.accenture.chickentest.repository.EggRepository;
import com.accenture.chickentest.repository.ParametroRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class EggServiceTest {


    @Mock
    private EggRepository eggRepository;
    @Mock
    private ParametroRepository parametroRepository;
    @InjectMocks
    private EggService eggService;
    @InjectMocks
    private ParametroService parametroService;



    private EggDTO eggDTO;

    private EggDTO eggDTO2;
    private Egg egg;
    private Egg egg2;
    private Egg egg3;
    private ParametroDTO parametroDTO;
    private ParametroDTO parametroDTO2;

    private List<ParametroDTO> parametrosDTO;
    private Parametro parametro;
    private Parametro parametro2;

    private List<Parametro> parametros;
    private List<Egg> listeggs ;
    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);

        eggService=new EggService( eggRepository,parametroRepository,parametroService);

        egg= new Egg();
        egg.setId(1L);
        egg.setPrice(10);
        egg.setDateFarm(new Date());

        egg2= new Egg();
        egg2.setId(2L);
        egg2.setPrice(10);
        egg2.setDateFarm(new Date());

        egg3= new Egg();
        egg3.setId(1L);
        egg3.setPrice(10);
        egg3.setStatus(Status.PUESTO);

        egg3.setDateFarm(new Date());
        egg3.setIdFarm(8L);


        eggDTO= new EggDTO();
        eggDTO.setId(1L);
        eggDTO.setPrice(10);
        eggDTO.setStatus(Status.PUESTO);
        eggDTO.setDateFarm(new Date());

        eggDTO2= new EggDTO();
        eggDTO2.setDateFarm(new Date());
        eggDTO2.setPrice(12);
        eggDTO2.setIdFarm(8L);

        parametrosDTO = new ArrayList<>();
        parametroDTO=new ParametroDTO();
        parametroDTO.setId(1L);
        parametroDTO.setClave("PrecioHuevos");
        parametroDTO.setValor(15L);

        parametroDTO2=new ParametroDTO();
        parametroDTO2.setId(2L);
        parametroDTO2.setClave("CapacidadMaximaGranja");
        parametroDTO2.setValor(2L);


        parametrosDTO.add(parametroDTO);
        parametrosDTO.add(parametroDTO2);

        parametros = new ArrayList<>();

        parametro=new ParametroDTO();
        parametro.setId(1L);
        parametro.setClave("PrecioHuevos");
        parametro.setValor(15L);

        parametro2=new ParametroDTO();
        parametro2.setId(2L);
        parametro2.setClave("CapacidadMaximaGranja");
        parametro2.setValor(2L);


        parametros.add(parametro);
        parametros.add(parametro2);

        listeggs=new ArrayList<>();

        listeggs.add(egg);
        listeggs.add(egg2);
        listeggs.add(egg3);

    }



    @Test
    void save() {

        ResponseEntity<EggDTO> responseEntity = ResponseEntity.of(Optional.empty());
        when(eggRepository.findById(egg.getId())).thenReturn(of(egg));
        when(parametroRepository.findByName("PrecioHuevos")).thenReturn(of(parametro));
        responseEntity = eggService.save(eggDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);


    }



    @Test
    void update() {
        ResponseEntity<EggDTO> responseEntity = ResponseEntity.of(Optional.empty());
        when(eggRepository.findById(egg.getId())).thenReturn(of(egg));
        responseEntity = eggService.update(1,eggDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    void deleteEgg() {
        ResponseEntity<EggDTO> responseEntity = ResponseEntity.of(Optional.empty());
        when(eggRepository.findById(egg.getId())).thenReturn(of(egg));
        responseEntity = eggService.deleteEgg(1);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }


    @Test
    void getEgg() {
        when(eggRepository.findById(egg.getId())).thenReturn(of(egg));
        EggDTO eggcompare=eggService.getEgg(1);
        assertEquals(10,eggcompare.getPrice());

    }

    @Test
    void getEggs() {
        when(eggRepository.findAll()).thenReturn(listeggs);
        assertEquals(3,eggService.getEggs().stream().count());
    }

    @Test
    void getEggsWithOutFarm() {
        when(eggRepository.findAll()).thenReturn(listeggs);

        assertEquals(2,eggService.getEggsWithOutFarm().stream().count());

    }

    @Test
    void getEggsWithFarm() {
        when(eggRepository.findAll()).thenReturn(listeggs);
        when(parametroRepository.findByName("CapacidadMaximaGranja")).thenReturn(of(parametro));

        assertEquals(1,eggService.getEggsWithFarm(8L).stream().count());
    }
}