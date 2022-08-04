package com.accenture.chickentest.service;

import com.accenture.chickentest.domain.dao.Chicken;
import com.accenture.chickentest.domain.dao.Farm;
import com.accenture.chickentest.domain.dto.ChickenDTO;
import com.accenture.chickentest.domain.dto.FarmDTO;
import com.accenture.chickentest.exception.ObjectNotFoundException;
import com.accenture.chickentest.mapper.ModelMapper;
import com.accenture.chickentest.repository.ChickenRepository;
import com.accenture.chickentest.repository.FarmRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BuySellService {


    private final ChickenRepository chickenRepository;
    private final FarmRepository farmRepository;



    public BuySellService(ChickenRepository chickenRepository, FarmRepository farmRepository) {
        this.chickenRepository = chickenRepository;
        this.farmRepository = farmRepository;
    }

    public ResponseEntity<ChickenDTO> buyChicken(ChickenDTO chickenDTO,Long id) {
         chickenDTO.setIdFarm(id);
        Chicken chicken =  ModelMapper.INSTANCE.DTOtoDaoChicken(chickenDTO);
        chickenRepository.save(chicken);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
    public ResponseEntity<FarmDTO> updateAmount(FarmDTO farmDTO,Long id) {

        Farm farm =  ModelMapper.INSTANCE.DTOtoDaoFarm(farmDTO);
        farmRepository.save(farm);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    public ResponseEntity<ChickenDTO>  sellChicken(long id) {
        Chicken chicken = chickenRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No existe id seleccionado, no se puede eliminar"));
        chickenRepository.delete(chicken);
        return  new ResponseEntity<>(HttpStatus.OK);

    }
}
