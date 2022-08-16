package com.accenture.chickentest.service;


import com.accenture.chickentest.domain.dao.Chicken;
import com.accenture.chickentest.domain.dao.Egg;
import com.accenture.chickentest.domain.dto.ChickenDTO;

import com.accenture.chickentest.domain.dto.EggDTO;
import com.accenture.chickentest.domain.enumStatus.Status;
import com.accenture.chickentest.mapper.ModelMapper;
import com.accenture.chickentest.repository.ChickenRepository;
import com.accenture.chickentest.repository.EggRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class TransformationService {
    private final ChickenRepository chickenRepository;
    private final EggRepository eggRepository;

    public TransformationService(ChickenRepository chickenRepository, EggRepository eggRepository) {
        this.chickenRepository = chickenRepository;
        this.eggRepository = eggRepository;
    }

    public ResponseEntity<EggDTO> eggToChicken(Long id) {
        List<EggDTO> eggs=eggRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOEgg)
                .collect(Collectors.toList());
        List<EggDTO> eggsTotal= new ArrayList<>();
        eggs.forEach(e -> {
            if(Objects.equals(e.getIdFarm(), id)  && (e.getStatus() == Status.COMPRADO)) {
            {

                eggsTotal.add(e);
            }
        }
        });
        eggsTotal.forEach(eg -> {
            System.out.print(eg.getAmountDays()>30);
            if(eg.getAmountDays()>30 && eg.getAmountDays() !=null)
            {
                eg.setStatus(Status.NACIDO);
                eg.setIdFarm(null);

                Egg egg =  ModelMapper.INSTANCE.DTOtoDaoEgg(eg);
                Chicken chicken=new Chicken();
                chicken.setAmountDays(0L);
                chicken.setDateFarm(new Date());
                chicken.setPrice(egg.getPrice());
                chicken.setIdFarm(egg.getIdFarm());
                chicken.setSexo(false);
                chicken.setStatus(Status.CONVERTIDO);
                chickenRepository.save(chicken);

                eggRepository.save(egg);
            }
        });

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    public ResponseEntity<ChickenDTO> chickenToDead(Long id) {
        List<ChickenDTO> chickens=chickenRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOChicken)
                .collect(Collectors.toList());
       List<ChickenDTO> chickensTotal= new ArrayList<>();
       chickens.forEach(ch -> {
            if(Objects.equals(ch.getIdFarm(), id)  && ((ch.getStatus() == Status.COMPRADO))||(ch.getStatus() == Status.CONVERTIDO)) {
                {

                    chickensTotal.add(ch);
                }
            }
        });
          chickensTotal.forEach(c -> {
             if(c.getAmountDays() !=null && c.getAmountDays()>100);
            {
              c.setStatus(Status.MUERTO);
                c.setIdFarm(null);

                Chicken chicken =  ModelMapper.INSTANCE.DTOtoDaoChicken(c);
chickenRepository.save(chicken);

            }

        });
        return new ResponseEntity<>(HttpStatus.CREATED);

    }
}
