package com.accenture.chickentest.service;


import com.accenture.chickentest.domain.dao.Chicken;
import com.accenture.chickentest.domain.dao.Egg;
import com.accenture.chickentest.domain.dao.Farm;
import com.accenture.chickentest.domain.dto.ChickenDTO;

import com.accenture.chickentest.domain.dto.EggDTO;
import com.accenture.chickentest.domain.dto.ParametroDTO;
import com.accenture.chickentest.domain.enumStatus.Status;
import com.accenture.chickentest.exception.ObjectNotFoundException;
import com.accenture.chickentest.mapper.ModelMapper;
import com.accenture.chickentest.repository.ChickenRepository;
import com.accenture.chickentest.repository.EggRepository;
import com.accenture.chickentest.repository.FarmRepository;
import com.accenture.chickentest.repository.ParametroRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class TransformationService {
    private final ChickenRepository chickenRepository;
    private final EggRepository eggRepository;
    private final FarmRepository farmRepository;
    private final ParametroRepository parametroRepository;
    private ParametroDTO daysPutAnEgg;
    private ParametroDTO daysLifeEgg;
    private ParametroDTO daysLifeChicken;
    private ParametroDTO priceEgg;
    private ParametroDTO priceChicken;

    private Long id;
    @Autowired
    private ParametroService parametroService;
    public TransformationService(ChickenRepository chickenRepository, EggRepository eggRepository, ParametroRepository parametroRepository,ParametroService parametroService,FarmRepository farmRepository) {
        this.chickenRepository = chickenRepository;
        this.eggRepository = eggRepository;
        this.parametroRepository = parametroRepository;
        this.parametroService=parametroService;
        this.farmRepository=farmRepository;

    }

    public ResponseEntity<EggDTO> eggToChicken() {
        Farm farm=farmRepository.findById(1L)
                .orElseThrow(() -> new ObjectNotFoundException("No existe Farm"));
        List<EggDTO> eggs = eggRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOEgg)
                .collect(Collectors.toList());

        List<EggDTO> eggsTotal = new ArrayList<>();
        daysLifeEgg=parametroService.getParametroIdByName("CantidadDiasVidaHuevo");
        priceChicken=parametroService.getParametroIdByName("PrecioPollos");

        eggs.forEach(e -> {
            if (Objects.equals(e.getIdFarm(), farm.getId()) && ((e.getStatus() == Status.COMPRADO) || (e.getStatus() == Status.PUESTO))) {
                {

                    eggsTotal.add(e);
                }
            }
        });
        eggsTotal.forEach(eg -> {
            if (eg.getAmountDays() >= daysLifeEgg.getValor()) {


                Egg egg = ModelMapper.INSTANCE.DTOtoDaoEgg(eg);
                Chicken chicken = new Chicken();
                chicken.setAmountDays(egg.getAmountDays());
                chicken.setDateFarm(egg.getDateFarm());
                chicken.setPrice(this.priceChicken.getValor());
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

    public ResponseEntity<ChickenDTO> chickenToDead() {

        Farm farm=farmRepository.findById(1L)
                .orElseThrow(() -> new ObjectNotFoundException("No existe Farm"));

        List<ChickenDTO> chickens = chickenRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOChicken)
                .collect(Collectors.toList());
        List<ChickenDTO> chickensTotal = new ArrayList<>();

        daysLifeChicken=parametroService.getParametroIdByName("CantidadDiasVidaPollo");


        chickens.forEach(ch -> {
            if (Objects.equals(ch.getIdFarm(), farm.getId()) && ((ch.getStatus() == Status.COMPRADO)) || (ch.getStatus() == Status.CONVERTIDO)) {
                {

                    chickensTotal.add(ch);
                }
            }
        });
       chickensTotal.forEach(c -> {

            if (c.getAmountDays() >= daysLifeChicken.getValor()) {
               c.setStatus(Status.MUERTO);
               c.setIdFarm(null);

                  Chicken chicken = ModelMapper.INSTANCE.DTOtoDaoChicken(c);
               chickenRepository.save(chicken);

            }

        });
        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    public ResponseEntity<ChickenDTO> putAnEgg() {
        Farm farm=farmRepository.findById(1L)
                .orElseThrow(() -> new ObjectNotFoundException("No existe Farm"));
        List<ChickenDTO> chickens = chickenRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOChicken)
                .collect(Collectors.toList());
        daysPutAnEgg=parametroService.getParametroIdByName("CantidadDiasPonerHuevo");
        priceEgg=parametroService.getParametroIdByName("PrecioHuevos");

        chickens.forEach(c -> {
            if (Objects.equals(c.getIdFarm(), farm.getId()) && (c.getStatus() == Status.COMPRADO) && (Objects.equals(c.getAmountDays(), daysPutAnEgg)) && (c.getSexo() == true)) {
                {
                    Egg egg = new Egg();
                    egg.setAmountDays(0L);
                    egg.setDateFarm(new Date());
                    egg.setPrice(this.priceEgg.getValor());
                    egg.setIdFarm(id);
                    egg.setStatus(Status.PUESTO);
                    eggRepository.save(egg);

                }
            }
        });

        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    public void updateDays() {
        List<EggDTO> eggs = eggRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOEgg)
                .collect(Collectors.toList());
        List<ChickenDTO> chickens = chickenRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOChicken)
                .collect(Collectors.toList());

        eggs.forEach(e -> {
            Date dateNow= new Date();

            Date dateCreate=e.getDateFarm();

            long diff =  ChronoUnit.DAYS.between(dateCreate.toInstant(),dateNow.toInstant());
            e.setAmountDays(diff);
                Egg egg = ModelMapper.INSTANCE.DTOtoDaoEgg(e);
                eggRepository.save(egg);

        });
        chickens.forEach(c -> {
            Date dateNow= new Date();

            Date dateCreate=c.getDateFarm();
            long diff =  ChronoUnit.DAYS.between(dateCreate.toInstant(),dateNow.toInstant());
            c.setAmountDays(diff);
            Chicken chicken = ModelMapper.INSTANCE.DTOtoDaoChicken(c);

            chickenRepository.save(chicken);



        });
    }
}