package com.accenture.chickentest.service;

import com.accenture.chickentest.domain.dao.Chicken;
import com.accenture.chickentest.domain.dao.Egg;
import com.accenture.chickentest.domain.dao.Farm;
import com.accenture.chickentest.domain.dto.ChickenDTO;
import com.accenture.chickentest.domain.dto.EggDTO;
import com.accenture.chickentest.domain.dto.FarmDTO;
import com.accenture.chickentest.exception.ObjectNotFoundException;
import com.accenture.chickentest.mapper.ModelMapper;
import com.accenture.chickentest.repository.ChickenRepository;
import com.accenture.chickentest.repository.EggRepository;
import com.accenture.chickentest.repository.FarmRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BuySellService {


    private final ChickenRepository chickenRepository;
    private final FarmRepository farmRepository;

    private final EggRepository eggRepository;


    public BuySellService(ChickenRepository chickenRepository, FarmRepository farmRepository,EggRepository eggRepository) {
        this.chickenRepository = chickenRepository;
        this.farmRepository = farmRepository;
        this.eggRepository = eggRepository;
    }

    public ResponseEntity<ChickenDTO> buyChicken(ChickenDTO chickenDTO,Long id) {
        chickenDTO.setIdFarm(id);
        Chicken chicken =  ModelMapper.INSTANCE.DTOtoDaoChicken(chickenDTO);
        chickenRepository.save(chicken);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    public ResponseEntity<EggDTO> buyEgg(EggDTO eggDTO, Long id) {
        eggDTO.setIdFarm(id);
        Egg egg =  ModelMapper.INSTANCE.DTOtoDaoEgg(eggDTO);
        eggRepository.save(egg);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    public ResponseEntity<ChickenDTO>  sellChicken(long id) {
        Chicken chicken = chickenRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No existe id seleccionado, no se puede eliminar"));
        chickenRepository.delete(chicken);
        return  new ResponseEntity<>(HttpStatus.OK);

    }

    public ResponseEntity<EggDTO>  sellEgg(long id) {
        Egg egg = eggRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No existe id seleccionado, no se puede eliminar"));
        eggRepository.delete(egg);
        return  new ResponseEntity<>(HttpStatus.OK);

    }
}
