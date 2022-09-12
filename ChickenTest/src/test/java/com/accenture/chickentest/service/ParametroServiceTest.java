package com.accenture.chickentest.service;

import com.accenture.chickentest.domain.dao.Parametro;
import com.accenture.chickentest.domain.dto.ChickenDTO;
import com.accenture.chickentest.domain.dto.ParametroDTO;
import com.accenture.chickentest.repository.ParametroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ParametroServiceTest {

    @Mock
    private ParametroRepository parametroRepository;

    @InjectMocks
    private ParametroService parametroService;

    private Parametro parametro;
    private Parametro parametro2;
    private ParametroDTO parametroDTO;

    private List<Parametro> parametros;
    private List<ParametroDTO> parametrosDTO;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
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

        parametrosDTO = new ArrayList<>();

        parametroDTO=new ParametroDTO();
        parametroDTO.setId(1L);
        parametroDTO.setClave("CapacidadMaximaGranja");
        parametroDTO.setValor(15L);
        parametrosDTO.add(parametroDTO);

    }
    @Test
    void save() {
        ResponseEntity<ParametroDTO> responseEntity = ResponseEntity.of(Optional.empty());
        when(parametroRepository.findById(parametro.getId())).thenReturn(of(parametro));
        responseEntity = parametroService.save(parametroDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void update() {
        ResponseEntity<ParametroDTO> responseEntity = ResponseEntity.of(Optional.empty());

        when(parametroRepository.findById(parametro.getId())).thenReturn(of(parametro));
        responseEntity = parametroService.update(1,parametroDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    void delete() {
        ResponseEntity<ParametroDTO> responseEntity = ResponseEntity.of(Optional.empty());
        when(parametroRepository.findById(parametro.getId())).thenReturn(of(parametro));
        responseEntity = parametroService.delete(1);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getParametros() {
        when(parametroRepository.findAll()).thenReturn(parametros);
        assertEquals(2,parametroService.getParametros().stream().count());
    }

    @Test
    void getParametro() {
        when(parametroRepository.findById(parametro.getId())).thenReturn(of(parametro));
        ParametroDTO parametrocompare=parametroService.getParametro(1);
        assertEquals("PrecioHuevos",parametrocompare.getClave());
    }


}