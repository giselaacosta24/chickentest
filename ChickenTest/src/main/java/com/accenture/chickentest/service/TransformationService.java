package com.accenture.chickentest.service;


import com.accenture.chickentest.domain.dao.Chicken;
import com.accenture.chickentest.domain.dao.Egg;
import com.accenture.chickentest.domain.dto.ChickenDTO;

import com.accenture.chickentest.domain.dto.EggDTO;
import com.accenture.chickentest.domain.enumStatus.Status;
import com.accenture.chickentest.mapper.ModelMapper;
import com.accenture.chickentest.repository.ChickenRepository;
import com.accenture.chickentest.repository.EggRepository;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
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
            if(Objects.equals(e.getIdFarm(), id)  && (e.getStatus() == Status.COMPRADO))  {
            {

                eggsTotal.add(e);
            }
        }
        });
        eggsTotal.forEach(eg -> {
            if(eg.getAmountDays()>30 && eg.getAmountDays() !=null )
            {


                Egg egg =  ModelMapper.INSTANCE.DTOtoDaoEgg(eg);
                Chicken chicken=new Chicken();
                chicken.setAmountDays(egg.getAmountDays());
                chicken.setDateFarm(egg.getDateFarm());
                chicken.setPrice(egg.getPrice());
                chicken.setIdFarm(egg.getIdFarm());
                chicken.setSexo(false);
                chicken.setStatus(Status.CONVERTIDO);
                chickenRepository.save(chicken);
                egg.setStatus(Status.NACIDO);
                egg.setIdFarm(null);

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
             if( c.getAmountDays()>100);
            {
              c.setStatus(Status.MUERTO);
                c.setIdFarm(null);

                Chicken chicken =  ModelMapper.INSTANCE.DTOtoDaoChicken(c);
chickenRepository.save(chicken);

            }

        });
        return new ResponseEntity<>(HttpStatus.CREATED);

    }



    public ResponseEntity<ChickenDTO> putAnEgg(Long id) {
        List<ChickenDTO> chickens=chickenRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOChicken)
                .collect(Collectors.toList());
        chickens.forEach(c -> {
            if(Objects.equals(c.getIdFarm(), id)  && (c.getStatus() == Status.COMPRADO)  && (c.getAmountDays() == 30) && (c.getSexo() == true))  {
                {
                  System.out.println("Estoy aca");
                    Egg egg=new Egg();
                    egg.setAmountDays(0L);
                    egg.setDateFarm(new Date());
                    egg.setPrice(10);
                    egg.setIdFarm(id);
                    egg.setStatus(Status.PUESTO);
                    eggRepository.save(egg);

                }
            }
        });



        return new ResponseEntity<>(HttpStatus.CREATED);

    }
}
