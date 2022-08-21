package com.accenture.chickentest.service;


import com.accenture.chickentest.domain.dao.Chicken;
import com.accenture.chickentest.domain.dao.Egg;
import com.accenture.chickentest.domain.dto.ChickenDTO;

import com.accenture.chickentest.domain.dto.EggDTO;
import com.accenture.chickentest.domain.dto.ParametroDTO;
import com.accenture.chickentest.domain.enumStatus.Status;
import com.accenture.chickentest.mapper.ModelMapper;
import com.accenture.chickentest.repository.ChickenRepository;
import com.accenture.chickentest.repository.EggRepository;
import com.accenture.chickentest.repository.ParametroRepository;

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

    private final ParametroRepository parametroRepository;
    private Long daysPutAnEgg;
    private Long daysLifeEgg;
    private Long daysLifeChicken;
    private Long priceEgg;
    private Long priceChicken;


    public TransformationService(ChickenRepository chickenRepository, EggRepository eggRepository, ParametroRepository parametroRepository) {
        this.chickenRepository = chickenRepository;
        this.eggRepository = eggRepository;
        this.parametroRepository = parametroRepository;
    }

    public ResponseEntity<EggDTO> eggToChicken(Long id) {
        List<ParametroDTO> parametros = parametroRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOParametro)
                .collect(Collectors.toList());

        List<EggDTO> eggs = eggRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOEgg)
                .collect(Collectors.toList());

        List<EggDTO> eggsTotal = new ArrayList<>();

        parametros.forEach(p -> {
            if (Objects.equals(p.getClave(), "CantidadDiasVidaHuevo")) {
                {
                    this.daysLifeEgg = p.getValor();

                }
            }
            if (Objects.equals(p.getClave(), "PrecioPollos")) {
                {
                    this.priceChicken = p.getValor();

                }
            }
        });


        eggs.forEach(e -> {
            if (Objects.equals(e.getIdFarm(), id) && ((e.getStatus() == Status.COMPRADO) || (e.getStatus() == Status.PUESTO))) {
                {

                    eggsTotal.add(e);
                }
            }
        });
        eggsTotal.forEach(eg -> {
            if (eg.getAmountDays() >= daysLifeEgg) {


                Egg egg = ModelMapper.INSTANCE.DTOtoDaoEgg(eg);
                Chicken chicken = new Chicken();
                chicken.setAmountDays(egg.getAmountDays());
                chicken.setDateFarm(egg.getDateFarm());
                chicken.setPrice(this.priceChicken);
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

        List<ParametroDTO> parametros = parametroRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOParametro)
                .collect(Collectors.toList());

        List<ChickenDTO> chickens = chickenRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOChicken)
                .collect(Collectors.toList());
        List<ChickenDTO> chickensTotal = new ArrayList<>();


        parametros.forEach(p -> {
            if (Objects.equals(p.getClave(), "CantidadDiasVidaPollo")) {
                {
                    this.daysLifeChicken = p.getValor();

                }
            }

        });

        chickens.forEach(ch -> {
            if (Objects.equals(ch.getIdFarm(), id) && ((ch.getStatus() == Status.COMPRADO)) || (ch.getStatus() == Status.CONVERTIDO)) {
                {

                    chickensTotal.add(ch);
                }
            }
        });
        chickensTotal.forEach(c -> {

            if (c.getAmountDays() >= daysLifeChicken) {
                c.setStatus(Status.MUERTO);
                c.setIdFarm(null);

                Chicken chicken = ModelMapper.INSTANCE.DTOtoDaoChicken(c);
                chickenRepository.save(chicken);

            }

        });
        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    public ResponseEntity<ChickenDTO> putAnEgg(Long id) {


        List<ParametroDTO> parametros = parametroRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOParametro)
                .collect(Collectors.toList());
        List<ChickenDTO> chickens = chickenRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOChicken)
                .collect(Collectors.toList());

        parametros.forEach(p -> {
            if (Objects.equals(p.getClave(), "CantidadDiasPonerHuevo")) {
                {
                    this.daysPutAnEgg = p.getValor();
                    System.out.println(this.daysPutAnEgg);

                }
            }
            if (Objects.equals(p.getClave(), "PrecioHuevos")) {
                {
                    this.priceEgg = p.getValor();

                }
            }
        });


        chickens.forEach(c -> {
            if (Objects.equals(c.getIdFarm(), id) && (c.getStatus() == Status.COMPRADO) && (c.getAmountDays() == daysPutAnEgg) && (c.getSexo() == true)) {
                {
                    Egg egg = new Egg();
                    egg.setAmountDays(0L);
                    egg.setDateFarm(new Date());
                    egg.setPrice(this.priceEgg);
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