package com.accenture.chickentest.service;

import com.accenture.chickentest.domain.dao.Chicken;
import com.accenture.chickentest.domain.dao.Farm;
import com.accenture.chickentest.domain.dto.ChickenDTO;
import com.accenture.chickentest.domain.dto.FarmDTO;
import com.accenture.chickentest.exception.ObjectNotFoundException;
import com.accenture.chickentest.mapper.ModelMapper;

import com.accenture.chickentest.repository.FarmRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class FarmService {

    private final FarmRepository farmRepository;
    public FarmService(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }
    public List<FarmDTO> getFarm()
    {
        return farmRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOFarm)
                .collect(Collectors.toList());

    }

    public FarmDTO getFarm(long id) {
        FarmDTO farmDTO=new FarmDTO();
        Farm farm = farmRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No existe farm"));

        if(farm.getId() !=  null)
            farmDTO = ModelMapper.INSTANCE.daoToDTOFarm(farm);

        return farmDTO;



    }

    public ResponseEntity<FarmDTO> save(FarmDTO farmDTO) {

        // convert DTO to Entity
        Farm farm =  ModelMapper.INSTANCE.DTOtoDaoFarm(farmDTO);
        farmRepository.save(farm);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
  /*  public Object save(Farm dbfarm) {
    }*/
    //transformar huevo a pollo

    //mostrar informacion granja completa
}
