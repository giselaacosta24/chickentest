package com.accenture.chickentest.service;

import com.accenture.chickentest.controller.MailController;
import com.accenture.chickentest.domain.dao.Egg;
import com.accenture.chickentest.domain.dto.ParametroDTO;
import com.accenture.chickentest.domain.enumStatus.Status;
import com.accenture.chickentest.exception.ObjectNotFoundException;
import com.accenture.chickentest.mapper.ModelMapper;
import com.accenture.chickentest.repository.EggRepository;
import com.accenture.chickentest.domain.dto.EggDTO;
import com.accenture.chickentest.repository.ParametroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EggService {


    private final EggRepository eggRepository;
    private final ParametroRepository parametroRepository;
    private Long priceEgg;

    private Long capacity;
    private static final Logger logger = LoggerFactory.getLogger(MailController.class);
    @Autowired
    private MailerService mailerService;
    public EggService(EggRepository eggRepository,ParametroRepository parametroRepository) {
        this.eggRepository = eggRepository;
        this.parametroRepository = parametroRepository;

    }


    public List<EggDTO> getEggs() {

        return eggRepository.findAll().stream().map(eggDTO -> ModelMapper.INSTANCE.daoToDTOEgg(eggDTO))
                .collect(Collectors.toList());

    }




    public ResponseEntity<EggDTO> save(EggDTO eggDTO) {

        // convert DTO to Entity
        Egg egg =  ModelMapper.INSTANCE.DTOtoDaoEgg(eggDTO);
        List<ParametroDTO> parametros = parametroRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOParametro)
                .collect(Collectors.toList());

        parametros.forEach(p -> {
            if (Objects.equals(p.getClave(), "PrecioHuevos")) {
                {
                    priceEgg = p.getValor();

                }
            }

        });

        egg.setPrice(priceEgg);
        eggRepository.save(egg);

        return new ResponseEntity<EggDTO>(HttpStatus.CREATED);

    }


    public EggDTO getEgg(long id) {
        EggDTO eggDTO=new EggDTO();
        Egg egg = eggRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No existe id seleccionado, no se puede modificar"));

        if(egg.getId() !=  null)
            eggDTO = ModelMapper.INSTANCE.daoToDTOEgg(egg);

        return eggDTO;



    }
    public ResponseEntity<EggDTO> update(long id,EggDTO eggDTO) {
        Egg eggDTORequest = ModelMapper.INSTANCE.DTOtoDaoEgg(eggDTO);

        Egg egg = eggRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No existe id seleccionado, no se puede modificar"));
        egg.setPrice(eggDTORequest.getPrice());
        egg.setAmountDays(eggDTORequest.getAmountDays());
        eggRepository.save(egg);
        return new ResponseEntity<EggDTO>(HttpStatus.OK);

    }




    public ResponseEntity<EggDTO>  deleteEgg(long id) {
        Egg egg = eggRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No existe id seleccionado, no se puede eliminar"));
        eggRepository.delete(egg);
        return  new ResponseEntity<>(HttpStatus.OK);

    }


    public List<EggDTO> getEggsWithOutFarm() {
        List<EggDTO> eggs=eggRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOEgg)
                .collect(Collectors.toList());
        List<EggDTO> eggswithoutfarm=new ArrayList<EggDTO>();
        eggs.forEach(e -> {
            System.out.print(e.getStatus() != Status.VENDIDO);
            if(e.getIdFarm()==null && e.getStatus() == null)  {

                eggswithoutfarm.add(e);
            }
        });
        return eggswithoutfarm;

    }


    public List<EggDTO> getEggsWithFarm(Long id) {
        List<EggDTO> eggs=eggRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOEgg)
                .collect(Collectors.toList());
        List<EggDTO> eggswithfarm=new ArrayList<EggDTO>();
        eggs.forEach(e -> {
            if(Objects.equals(e.getIdFarm(), id)  && ((e.getStatus() == Status.COMPRADO) ||(e.getStatus() == Status.PUESTO))) {

                eggswithfarm.add(e);
            }
        });

        List<ParametroDTO> parametros = parametroRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOParametro)
                .collect(Collectors.toList());

        parametros.forEach(p -> {
            if (Objects.equals(p.getClave(), "CapacidadMaximaGranja")) {
                {
                    capacity = p.getValor();

                }
            }

        });

        if(eggswithfarm.stream().count()==capacity || eggswithfarm.stream().count() > capacity) {
            try {
                mailerService.sendNotification();
            } catch (Exception e) {
                // catch error
                logger.info("Error Sending Email: " + e.getMessage());
            }
        }
        return eggswithfarm;

    }


}
