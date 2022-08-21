package com.accenture.chickentest.service;

import com.accenture.chickentest.domain.dao.Chicken;
import com.accenture.chickentest.domain.dao.Parametro;
import com.accenture.chickentest.domain.dto.ChickenDTO;
import com.accenture.chickentest.domain.dto.ParametroDTO;
import com.accenture.chickentest.exception.ObjectNotFoundException;
import com.accenture.chickentest.mapper.ModelMapper;
import com.accenture.chickentest.repository.ParametroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParametroService {

    private final ParametroRepository parametroRepository;

    public ParametroService(ParametroRepository parametroRepository) {
        this.parametroRepository = parametroRepository;
    }


    public ResponseEntity<ParametroDTO> save(ParametroDTO parametroDTO) {

        // convert DTO to Entity
        Parametro parametro =  ModelMapper.INSTANCE.DTOtoDaoParametro(parametroDTO);

        parametroRepository.save(parametro);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    public ResponseEntity<ParametroDTO> update(long id, ParametroDTO parametroDTO) {
        Parametro parametroRequest = ModelMapper.INSTANCE.DTOtoDaoParametro(parametroDTO);

        Parametro parametro = parametroRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No existe id seleccionado, no se puede modificar"));
        parametro.setClave(parametroRequest.getClave());
        parametro.setValor(parametroRequest.getValor());

        parametroRepository.save(parametro);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    public ResponseEntity<ParametroDTO>  delete(long id) {
        Parametro parametro = parametroRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No existe id seleccionado, no se puede eliminar"));
        parametroRepository.delete(parametro);
        return  new ResponseEntity<>(HttpStatus.OK);

    }

    public List<ParametroDTO> getParametros() {

        return parametroRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOParametro)
                .collect(Collectors.toList());

    }

    public ParametroDTO getParametro(long id) {
        ParametroDTO parametroDTO=new ParametroDTO();
        Parametro parametro = parametroRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No existe id seleccionado, no se puede modificar"));

        if(parametro.getId() !=  null)
            parametroDTO = ModelMapper.INSTANCE.daoToDTOParametro(parametro);

        return parametroDTO;



    }

}