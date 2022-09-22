package com.accenture.chickentest.service;

import com.accenture.chickentest.domain.dao.Chicken;
import com.accenture.chickentest.domain.dao.Egg;
import com.accenture.chickentest.domain.dao.Transaction;
import com.accenture.chickentest.domain.dto.ChickenDTO;
import com.accenture.chickentest.domain.dto.EggDTO;
import com.accenture.chickentest.domain.dto.FarmDTO;
import com.accenture.chickentest.domain.dto.ParametroDTO;
import com.accenture.chickentest.domain.enumStatus.Status;
import com.accenture.chickentest.exception.ObjectNotFoundException;
import com.accenture.chickentest.mapper.ModelMapper;
import com.accenture.chickentest.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BuySellService {


    private final ChickenRepository chickenRepository;
    private final EggRepository eggRepository;
    private final FarmRepository farmRepository;

    private final TransactionRepository transactionRepository;

    private final ParametroRepository parametroRepository;
    private ParametroDTO capacityFarm;
    private Long countFarm;

   @Autowired
    private FarmService farmService;
    @Autowired
    private ParametroService parametroService;

    public BuySellService(ChickenRepository chickenRepository, EggRepository eggRepository, TransactionRepository transactionRepository, ParametroRepository parametroRepository, FarmRepository farmRepository, FarmService farmService,ParametroService parametroService) {
        this.chickenRepository = chickenRepository;
        this.eggRepository = eggRepository;
        this.transactionRepository = transactionRepository;
        this.parametroRepository = parametroRepository;
        this.farmRepository=farmRepository;
        this.farmService=farmService;
        this.parametroService=parametroService;

    }



    public ResponseEntity<ChickenDTO> buyChicken(ChickenDTO chickenDTO,Long id) {

        List<ChickenDTO> chickens=chickenRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOChicken)
                .collect(Collectors.toList());
        List<ChickenDTO> chickenswithfarm= new ArrayList<>();
        chickens.forEach(c -> {
            if(Objects.equals(c.getIdFarm(), id) && ((c.getStatus() == Status.COMPRADO)||(c.getStatus() == Status.CONVERTIDO) ))
            {

                chickenswithfarm.add(c);
            }
        });
        countFarm=chickenswithfarm.stream().count();

        FarmDTO farmDTO=farmService.getFarm(id);

        capacityFarm=parametroService.getParametroIdByName("CapacidadMaximaGranja");


        if (countFarm < capacityFarm.getValor()) {

            chickenDTO.setIdFarm(id);
            chickenDTO.setStatus(Status.COMPRADO);

            Chicken chicken = ModelMapper.INSTANCE.DTOtoDaoChicken(chickenDTO);

            Transaction transaction = new Transaction();

            transaction.setPrice(chickenDTO.getPrice());
            transaction.setTypeProduct("Chicken");
            transaction.setTypeTransaction("Compra");
            transaction.setDateTransaction(new Date());
            transactionRepository.save(transaction);
            chickenRepository.save(chicken);
            farmService.updateAmount("compra",farmDTO.getId(),farmDTO,chicken.getPrice());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else {



            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);

        }
    }


    public ResponseEntity<EggDTO> buyEgg(EggDTO eggDTO, Long id) {
        FarmDTO farmDTO=farmService.getFarm(id);

        List<EggDTO> eggs=eggRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOEgg)
                .collect(Collectors.toList());
        List<EggDTO> eggswithfarm= new ArrayList<>();
        eggs.forEach(e -> {
            if(Objects.equals(e.getIdFarm(), id)  && ((e.getStatus() == Status.COMPRADO) ||(e.getStatus() == Status.PUESTO)))
            {

                eggswithfarm.add(e);
            }
        });

        countFarm=eggswithfarm.stream().count();
        capacityFarm=parametroService.getParametroIdByName("CapacidadMaximaGranja");


        if (countFarm < capacityFarm.getValor()) {

            eggDTO.setIdFarm(id);
            eggDTO.setStatus(Status.COMPRADO);
            Egg egg = ModelMapper.INSTANCE.DTOtoDaoEgg(eggDTO);

            Transaction transaction = new Transaction();
            transaction.setPrice(eggDTO.getPrice());
            transaction.setTypeProduct("Egg");
            transaction.setTypeTransaction("Compra");
            transaction.setDateTransaction(new Date());
            transactionRepository.save(transaction);
            farmService.updateAmount("compra",farmDTO.getId(),farmDTO,egg.getPrice());

            eggRepository.save(egg);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else {

            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);

        }
    }

    public ResponseEntity<ChickenDTO>  sellChicken(long id) {
        Chicken chicken = chickenRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No existe id seleccionado, no se puede eliminar"));

        Transaction transaction=new Transaction();
        transaction.setPrice(chicken.getPrice());
        transaction.setTypeProduct("Chicken");
        transaction.setTypeTransaction("Venta");
        transaction.setDateTransaction(new Date());
        transactionRepository.save(transaction);

        chicken.setStatus(Status.VENDIDO);
        System.out.println(chicken.getPrice());
        chickenRepository.save(chicken);

        return  new ResponseEntity<>(HttpStatus.OK);

    }

    public ResponseEntity<EggDTO>  sellEgg(long id) {
        Egg egg = eggRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No existe id seleccionado, no se puede eliminar"));
        Transaction transaction=new Transaction();

        transaction.setPrice(egg.getPrice());
        transaction.setTypeProduct("Egg");
        transaction.setTypeTransaction("Venta");
        transaction.setDateTransaction(new Date());
        transactionRepository.save(transaction);

        egg.setStatus(Status.VENDIDO);
        eggRepository.save(egg);
        return  new ResponseEntity<>(HttpStatus.OK);

    }
}
