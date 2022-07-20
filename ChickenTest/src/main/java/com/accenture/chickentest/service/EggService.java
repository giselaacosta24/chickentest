package com.accenture.chickentest.service;

import com.accenture.chickentest.domain.dao.Chicken;
import com.accenture.chickentest.domain.dao.Egg;
import com.accenture.chickentest.domain.dto.ChickenDTO;
import com.accenture.chickentest.exception.ObjectNotFoundException;
import com.accenture.chickentest.mapper.ModelMapper;
import com.accenture.chickentest.repository.EggRepository;
import com.accenture.chickentest.domain.dto.EggDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EggService {


    private final EggRepository eggRepository;

    public EggService(EggRepository eggRepository) {
        this.eggRepository = eggRepository;
    }


    public List<EggDTO> getEggs() {

        return eggRepository.findAll().stream().map(eggDTO -> ModelMapper.INSTANCE.daoToDTOEgg(eggDTO))
                .collect(Collectors.toList());

    }




    public ResponseEntity<EggDTO> save(EggDTO eggDTO) {

        // convert DTO to Entity
        Egg egg =  ModelMapper.INSTANCE.DTOtoDaoEgg(eggDTO);
        eggRepository.save(egg);

        return new ResponseEntity<EggDTO>(HttpStatus.CREATED);

    }



    public ResponseEntity<EggDTO> update(long id,EggDTO eggDTO) {
        Egg eggDTORequest = ModelMapper.INSTANCE.DTOtoDaoEgg(eggDTO);

        Egg egg = eggRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No existe id seleccionado, no se puede modificar"));
        egg.setPrice(eggDTORequest.getPrice());
        egg.setAmountDays(eggDTORequest.getAmountDays());
        egg.setIdFarmer(eggDTORequest.getIdFarmer());
        eggRepository.save(egg);
        return new ResponseEntity<EggDTO>(HttpStatus.OK);

    }


    public ResponseEntity<EggDTO>  deleteEgg(long id) {
        Egg egg = eggRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No existe id seleccionado, no se puede eliminar"));
        eggRepository.delete(egg);
        return  new ResponseEntity<>(HttpStatus.OK);

    }



}
