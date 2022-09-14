package com.accenture.chickentest.service;

import com.accenture.chickentest.domain.dao.Parametro;
import com.accenture.chickentest.domain.dto.ParametroDTO;
import com.accenture.chickentest.exception.ObjectNotFoundException;
import com.accenture.chickentest.mapper.ModelMapper;
import com.accenture.chickentest.repository.ParametroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
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

        List<ParametroDTO> totalParametros=new ArrayList<>();

        totalParametros=parametroRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOParametro)
                .collect(Collectors.toList());
        if (totalParametros.isEmpty()) {
            throw new ObjectNotFoundException("No existen parametros en BD");
        }
        return totalParametros;



    }

    public ParametroDTO getParametro(long id) {
        ParametroDTO parametroDTO=new ParametroDTO();
        Parametro parametro = parametroRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No existe id seleccionado, no se puede modificar"));

        if(parametro.getId() !=  null)
            parametroDTO = ModelMapper.INSTANCE.daoToDTOParametro(parametro);

        return parametroDTO;



    }

    public ParametroDTO getParametroIdByName(String clave) {
        ParametroDTO parametroDTO=new ParametroDTO();
        Parametro parametro = parametroRepository.findByName(clave).orElseThrow(() -> new ObjectNotFoundException("No existe parametro"));
        if(parametro.getId() !=  null)
            parametroDTO = ModelMapper.INSTANCE.daoToDTOParametro(parametro);

        return parametroDTO;

    }


}
