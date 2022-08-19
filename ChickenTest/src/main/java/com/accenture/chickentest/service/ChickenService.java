package com.accenture.chickentest.service;

import com.accenture.chickentest.domain.dao.Chicken;
import com.accenture.chickentest.domain.dto.ChickenDTO;
import com.accenture.chickentest.domain.enumStatus.Status;
import com.accenture.chickentest.exception.ObjectNotFoundException;
import com.accenture.chickentest.mapper.ModelMapper;
import com.accenture.chickentest.repository.ChickenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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


    public List<ChickenDTO> getChickensWithOutFarm() {
        List<ChickenDTO> chickens=chickenRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOChicken)
                .collect(Collectors.toList());
        List<ChickenDTO> chickenswithoutfarm= new ArrayList<>();
        chickens.forEach(c -> {
            if(c.getIdFarm()==null && (c.getStatus()==Status.CONVERTIDO || c.getStatus()==null) ){

                chickenswithoutfarm.add(c);
            }
        });
        return chickenswithoutfarm;

    }


    public List<ChickenDTO> getChickensWithFarm(Long id) {
        List<ChickenDTO> chickens=chickenRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOChicken)
                .collect(Collectors.toList());
        List<ChickenDTO> chickenswithfarm= new ArrayList<>();
        chickens.forEach(c -> {
            if(Objects.equals(c.getIdFarm(), id) && ((c.getStatus() == Status.COMPRADO)||(c.getStatus() == Status.CONVERTIDO) ))
            {

                chickenswithfarm.add(c);
            }
        });
        return chickenswithfarm;

    }




    public  ResponseEntity<ChickenDTO>  save(ChickenDTO chickenDTO) {

        // convert DTO to Entity
        Chicken chicken =  ModelMapper.INSTANCE.DTOtoDaoChicken(chickenDTO);
        chicken.setAmountDays(0L);

        chickenRepository.save(chicken);

       return new ResponseEntity<>(HttpStatus.CREATED);

    }

    public ChickenDTO getChicken(long id) {
        ChickenDTO chickenDTO=new ChickenDTO();
        Chicken chicken = chickenRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No existe id seleccionado, no se puede modificar"));

        if(chicken.getId() !=  null)
            chickenDTO = ModelMapper.INSTANCE.daoToDTOChicken(chicken);

        return chickenDTO;



    }

   public ResponseEntity<ChickenDTO> update(long id, ChickenDTO chickenDTO) {
       Chicken chickenDTORequest = ModelMapper.INSTANCE.DTOtoDaoChicken(chickenDTO);

       Chicken chicken = chickenRepository.findById(id)
               .orElseThrow(() -> new ObjectNotFoundException("No existe id seleccionado, no se puede modificar"));
       chicken.setPrice(chickenDTORequest.getPrice());
       chicken.setAmountDays(chickenDTORequest.getAmountDays());

       chickenRepository.save(chicken);
       return new ResponseEntity<>(HttpStatus.OK);

   }


    public ResponseEntity<ChickenDTO>  deleteChicken(long id) {
        Chicken chicken = chickenRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No existe id seleccionado, no se puede eliminar"));
        chickenRepository.delete(chicken);
        return  new ResponseEntity<>(HttpStatus.OK);

   }


}
