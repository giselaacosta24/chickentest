package com.accenture.chickentest.service;

import com.accenture.chickentest.domain.dao.Chicken;
import com.accenture.chickentest.domain.dao.Egg;
import com.accenture.chickentest.domain.dao.Farm;
import com.accenture.chickentest.domain.dto.ChickenDTO;
import com.accenture.chickentest.domain.dto.EggDTO;
import com.accenture.chickentest.domain.dto.FarmDTO;
import com.accenture.chickentest.exception.ObjectNotFoundException;
import com.accenture.chickentest.mapper.ModelMapper;

import com.accenture.chickentest.repository.EggRepository;
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
    public List<FarmDTO> getFarms() {

        return farmRepository.findAll().stream().map(farmDTO -> ModelMapper.INSTANCE.daoToDTOFarm(farmDTO))
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

    public FarmDTO getFarmIdByName(String name) {
        FarmDTO farmDTO=new FarmDTO();
        Farm farm = farmRepository.findByName(name);

        if(farm.getId() !=  null)
            farmDTO = ModelMapper.INSTANCE.daoToDTOFarm(farm);

        else {
            new ObjectNotFoundException("No existe farm");
        }

        return farmDTO;



    }

    public ResponseEntity<FarmDTO> save(FarmDTO farmDTO) {

        // convert DTO to Entity
        Farm farm =  ModelMapper.INSTANCE.DTOtoDaoFarm(farmDTO);
        farmRepository.save(farm);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    public ResponseEntity<FarmDTO> update(long id,FarmDTO farmDTO) {
        Farm farmDTORequest = ModelMapper.INSTANCE.DTOtoDaoFarm(farmDTO);

        Farm farm = farmRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No existe id seleccionado, no se puede modificar"));
        farm.setName(farmDTORequest.getName());
        farm.setEstimate(farmDTORequest.getEstimate());
        farmRepository.save(farm);
        return new ResponseEntity<FarmDTO>(HttpStatus.OK);

    }


    public ResponseEntity<FarmDTO>  deleteFarm(long id) {
        Farm farm = farmRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No existe id seleccionado, no se puede eliminar"));
        farmRepository.delete(farm);
        return  new ResponseEntity<>(HttpStatus.OK);

    }

}
