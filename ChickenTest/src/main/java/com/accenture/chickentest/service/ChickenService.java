package com.accenture.chickentest.service;

import com.accenture.chickentest.domain.dao.Chicken;
import com.accenture.chickentest.domain.dto.ChickenDTO;
import com.accenture.chickentest.exception.ObjectNotFoundException;
import com.accenture.chickentest.mapper.ModelMapper;
import com.accenture.chickentest.repository.ChickenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChickenService {

    private final ChickenRepository chickenRepository;

    public ChickenService(ChickenRepository chickenRepository) {
        this.chickenRepository = chickenRepository;
    }


    public List<ChickenDTO> getChickens() {

        return chickenRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOChicken)
                .collect(Collectors.toList());

    }




    public  ResponseEntity<ChickenDTO>  save(ChickenDTO chickenDTO) {

        // convert DTO to Entity
        Chicken chicken =  ModelMapper.INSTANCE.DTOtoDaoChicken(chickenDTO);
        chickenRepository.save(chicken);

       return new ResponseEntity<>(HttpStatus.CREATED);

    }



   public ResponseEntity<ChickenDTO> update(long id, ChickenDTO chickenDTO) {
       Chicken chickenDTORequest = ModelMapper.INSTANCE.DTOtoDaoChicken(chickenDTO);

       Chicken chicken = chickenRepository.findById(id)
               .orElseThrow(() -> new ObjectNotFoundException("No existe id seleccionado, no se puede modificar"));
       chicken.setPrice(chickenDTORequest.getPrice());
       chicken.setAmountDays(chickenDTORequest.getAmountDays());
       chicken.setIdFarmer(chickenDTORequest.getIdFarmer());
       chickenRepository.save(chicken);
       return new ResponseEntity<>(HttpStatus.OK);

   }


    public void deleteChicken(long id) {
        Chicken chicken = chickenRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No existe id seleccionado, no se puede eliminar"));

        chickenRepository.delete(chicken);
   }
}
