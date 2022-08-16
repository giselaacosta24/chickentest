package com.accenture.chickentest.service;

import com.accenture.chickentest.domain.dao.Chicken;
import com.accenture.chickentest.domain.dao.Egg;
import com.accenture.chickentest.domain.dao.Transaction;
import com.accenture.chickentest.domain.dto.ChickenDTO;
import com.accenture.chickentest.domain.dto.EggDTO;
import com.accenture.chickentest.domain.enumStatus.Status;
import com.accenture.chickentest.exception.ObjectNotFoundException;
import com.accenture.chickentest.mapper.ModelMapper;
import com.accenture.chickentest.repository.ChickenRepository;
import com.accenture.chickentest.repository.EggRepository;
import com.accenture.chickentest.repository.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class BuySellService {


    private final ChickenRepository chickenRepository;
    private final EggRepository eggRepository;

    private final TransactionRepository transactionRepository;

    public BuySellService(ChickenRepository chickenRepository, EggRepository eggRepository,TransactionRepository transactionRepository) {
        this.chickenRepository = chickenRepository;
        this.eggRepository = eggRepository;
        this.transactionRepository = transactionRepository;

    }

    public ResponseEntity<ChickenDTO> buyChicken(ChickenDTO chickenDTO,Long id) {
        chickenDTO.setIdFarm(id);
        chickenDTO.setStatus(Status.COMPRADO);
        Date dateNow= new Date();
        Date dateCreate=chickenDTO.getDateFarm();
        long diff =  ChronoUnit.DAYS.between(dateCreate.toInstant(),dateNow.toInstant());
        chickenDTO.setAmountDays(diff);
        Chicken chicken =  ModelMapper.INSTANCE.DTOtoDaoChicken(chickenDTO);
        Transaction transaction=new Transaction();

        transaction.setPrice(chickenDTO.getPrice());
        transaction.setTypeProduct("Chicken");
        transaction.setTypeTransaction("Compra");
        transaction.setDateTransaction(new Date());
        transactionRepository.save(transaction);
        chickenRepository.save(chicken);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    public ResponseEntity<EggDTO> buyEgg(EggDTO eggDTO, Long id) {
        eggDTO.setIdFarm(id);

        eggDTO.setStatus(Status.COMPRADO);

        Date dateNow= new Date();
        Date dateCreate=eggDTO.getDateFarm();
        long diff =  ChronoUnit.DAYS.between(dateCreate.toInstant(),dateNow.toInstant());
        eggDTO.setAmountDays(diff);
        Egg egg =  ModelMapper.INSTANCE.DTOtoDaoEgg(eggDTO);
        Transaction transaction=new Transaction();

        transaction.setPrice(eggDTO.getPrice());
        transaction.setTypeProduct("Egg");
        transaction.setTypeTransaction("Compra");
        transaction.setDateTransaction(new Date());
        transactionRepository.save(transaction);

        eggRepository.save(egg);

        return new ResponseEntity<>(HttpStatus.CREATED);

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

        chickenRepository.save(chicken);

        return  new ResponseEntity<>(HttpStatus.OK);

    }

    public ResponseEntity<EggDTO>  sellEgg(long id) {
        Egg egg = eggRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No existe id seleccionado, no se puede eliminar"));
        Transaction transaction=new Transaction();
        egg.setStatus(Status.VENDIDO);
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
