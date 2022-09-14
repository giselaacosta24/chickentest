package com.accenture.chickentest.service;


import com.accenture.chickentest.domain.dao.Farm;

import com.accenture.chickentest.domain.dto.FarmDTO;
import com.accenture.chickentest.domain.dto.ParametroDTO;
import com.accenture.chickentest.exception.ObjectNotFoundException;
import com.accenture.chickentest.mapper.ModelMapper;

import com.accenture.chickentest.repository.FarmRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Service
public class FarmService {

    private final FarmRepository farmRepository;
    public FarmService(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }

    public List<FarmDTO> getFarms() {


        List<FarmDTO> totalFarms=new ArrayList<>();

        totalFarms=farmRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOFarm)
                .collect(Collectors.toList());
        if (totalFarms.isEmpty()) {
            throw new ObjectNotFoundException("No existen farms en BD");
        }
        return totalFarms;


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
    public ResponseEntity<FarmDTO> updateAmount(String tipo,long id,FarmDTO farmDTO,double number) {

        Farm farmDTORequest = ModelMapper.INSTANCE.DTOtoDaoFarm(farmDTO);

        Farm farm = farmRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No existe id seleccionado, no se puede modificar"));
        double estimate=farm.getEstimate();
        if(Objects.equals(tipo, "compra")) {
            double amount = estimate - number;
            farm.setEstimate(amount);

        }
         else if(Objects.equals(tipo, "venta")) {
            double amount = estimate + number;
            farm.setEstimate(amount);

        }
         else{
            farm.setEstimate(estimate);

        }
         farm.setName(farmDTORequest.getName());



        if(farm.getEstimate()> 0 ) {

            farmRepository.save(farm);
            return new ResponseEntity<FarmDTO>(HttpStatus.OK);
        }
        else
        {
           throw new RuntimeException("Amount not available");
        }
    }

    public ResponseEntity<FarmDTO>  deleteFarm(long id) {
        Farm farm = farmRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No existe id seleccionado, no se puede eliminar"));
        farmRepository.delete(farm);
        return  new ResponseEntity<>(HttpStatus.OK);

    }

}
