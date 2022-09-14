package com.accenture.chickentest.service;

import com.accenture.chickentest.domain.dto.TransactionDTO;
import com.accenture.chickentest.exception.ObjectNotFoundException;
import com.accenture.chickentest.mapper.ModelMapper;
import com.accenture.chickentest.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionDTO> getTransactionsBuys()
    {
        List<TransactionDTO> transactionsList= transactionRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOTransaction)
                .collect(Collectors.toList());


        List<TransactionDTO> transactionsBuys=new ArrayList<TransactionDTO>();
        transactionsList.forEach(t -> {
            if(Objects.equals(t.getTypeTransaction(), "Compra")) {

                transactionsBuys.add(t);
            }
        });

        if (transactionsBuys.isEmpty()) {
            throw new ObjectNotFoundException("No existen transacciones de compras en BD");
        }
        return transactionsBuys;

    }
    public List<TransactionDTO> getTransactionsSells()
    {
        List<TransactionDTO> transactionsList= transactionRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOTransaction)
                .collect(Collectors.toList());

        List<TransactionDTO> transactionsSells=new ArrayList<TransactionDTO>();
        transactionsList.forEach(t -> {
            if(Objects.equals(t.getTypeTransaction(), "Venta")) {
                transactionsSells.add(t);
            }
        });
        if (transactionsSells.isEmpty()) {
            throw new ObjectNotFoundException("No existen transacciones de ventas en BD");
        }
        return transactionsSells;
    }


    public double  getAmountBuys()
    {
        List<TransactionDTO> transactionsBuys= transactionRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOTransaction)
                .collect(Collectors.toList());

        AtomicReference<Double> amountAll= new AtomicReference<>((double) 0);
        transactionsBuys.forEach(t -> {
            if(Objects.equals(t.getTypeTransaction(), "Compra")) {

                amountAll.updateAndGet(v -> (double) (v + t.getPrice()));
            }
        });
        return amountAll.get();

    }
    public double  getAmountSells()
    {
        List<TransactionDTO> transactionsSells= transactionRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOTransaction)
                .collect(Collectors.toList());


        AtomicReference<Double> amountAll= new AtomicReference<>((double) 0);
        transactionsSells.forEach(t -> {
            if(Objects.equals(t.getTypeTransaction(), "Venta")) {

                amountAll.updateAndGet(v -> (double) (v + t.getPrice()));
            }
        });
        return amountAll.get();

    }

    public int  getNumberTransactions(String transaction,String product)
    {
        List<TransactionDTO> transactionsChickenBuys= transactionRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOTransaction)
                .collect(Collectors.toList());

            int numberTransactions = (int) transactionsChickenBuys.stream().filter(t -> Objects.equals(t.getTypeTransaction(), transaction) && Objects.equals(t.getTypeProduct(), product)).count();

        return numberTransactions;


    }

    public List<TransactionDTO> getTransactions()
    {
        List<TransactionDTO> transactionsList= transactionRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOTransaction)
                .collect(Collectors.toList());

        return transactionsList;
    }





}
