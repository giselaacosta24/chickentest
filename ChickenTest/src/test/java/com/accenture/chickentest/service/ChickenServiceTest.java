package com.accenture.chickentest.service;

import com.accenture.chickentest.domain.dao.Chicken;
import com.accenture.chickentest.domain.dao.Transaction;
import com.accenture.chickentest.domain.dto.ChickenDTO;
import com.accenture.chickentest.domain.dto.ParametroDTO;
import com.accenture.chickentest.domain.enumStatus.Status;
import com.accenture.chickentest.repository.ChickenRepository;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ChickenServiceTest {


    @Mock
    private  ChickenRepository chickenRepository;

    @Mock
    private ParametroRepository parametroRepository;
    @InjectMocks
    private ChickenService chickenService;
    @InjectMocks
    private ParametroService parametroService;

    private Chicken chicken;
    private Chicken chicken2;
    private Chicken chicken3;
    private Chicken chicken4;

    private ChickenDTO chickenDTO;

    private ParametroDTO parametro;
    private ParametroDTO parametro2;
    private List<ParametroDTO> parametros;

    private List<Chicken> listchickens ;


    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);

        chickenService=new ChickenService( chickenRepository,parametroRepository,parametroService);

        chicken= new Chicken();
        chicken.setId(1L);
        chicken.setPrice(10);
        chicken.setSexo(false);
        chicken.setDateFarm(new Date());

        chicken2= new Chicken();
        chicken2.setId(2L);
        chicken2.setPrice(12);
        chicken2.setSexo(false);
        chicken2.setDateFarm(new Date());

        chicken3= new Chicken();
        chicken3.setId(3L);
        chicken3.setPrice(14);
        chicken3.setSexo(false);
        chicken3.setDateFarm(new Date());

        chicken4= new Chicken();
        chicken4.setId(4L);
        chicken4.setPrice(14);
        chicken4.setSexo(false);
        chicken4.setDateFarm(new Date());
        chicken4.setIdFarm(8L);
        chicken4.setStatus(Status.COMPRADO);

        chickenDTO= new ChickenDTO();
        chickenDTO.setPrice(10);
        chickenDTO.setSexo(false);
        chickenDTO.setDateFarm(new Date());



        parametros = new ArrayList<>();
        parametro=new ParametroDTO();
        parametro.setId(1L);
        parametro.setClave("PrecioPollos");
        parametro.setValor(15L);

        parametro2=new ParametroDTO();
        parametro2.setId(2L);
        parametro2.setClave("CapacidadMaximaGranja");
        parametro2.setValor(4L);

        parametros.add(parametro);

        parametros.add(parametro2);

        listchickens = new ArrayList<Chicken>();


        listchickens.add(chicken);
        listchickens.add(chicken2);
        listchickens.add(chicken3);
        listchickens.add(chicken4);
    }



    @Test
    void getChickens(){

        when(chickenRepository.findAll()).thenReturn(listchickens);
        assertEquals(4,chickenService.getChickens().stream().count());    }

    @Test
    void getChickensWithOutFarm() {
        when(chickenRepository.findAll()).thenReturn(listchickens);
        assertEquals(3,chickenService.getChickensWithOutFarm().stream().count());
    }

    @Test
    void getChickensWithFarm() {
        when(chickenRepository.findAll()).thenReturn(listchickens);
        when(parametroRepository.findByName("CapacidadMaximaGranja")).thenReturn(of(parametro));

        assertEquals(1,chickenService.getChickensWithFarm(8L).stream().count());
    }

    @Test
    void save() {

        ResponseEntity<ChickenDTO> responseEntity = ResponseEntity.of(Optional.empty());
        when(chickenRepository.findById(chicken.getId())).thenReturn(of(chicken));
        when(parametroRepository.findByName("PrecioPollos")).thenReturn(of(parametro));
        responseEntity = chickenService.save(chickenDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void getChicken() {
        when(chickenRepository.findById(chicken.getId())).thenReturn(of(chicken));
        ChickenDTO chickencompare=chickenService.getChicken(1);
        assertEquals(chicken.getPrice(),chickencompare.getPrice());

    }

    @Test
    void update() {
        ResponseEntity<ChickenDTO> responseEntity = ResponseEntity.of(Optional.empty());
        when(chickenRepository.findById(chicken.getId())).thenReturn(of(chicken));
        responseEntity = chickenService.update(1,chickenDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    void deleteChicken() {
        ResponseEntity<ChickenDTO> responseEntity = ResponseEntity.of(Optional.empty());
        when(chickenRepository.findById(chicken.getId())).thenReturn(of(chicken));
        responseEntity = chickenService.deleteChicken(1);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
}