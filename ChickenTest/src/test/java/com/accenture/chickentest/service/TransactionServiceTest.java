package com.accenture.chickentest.service;

import com.accenture.chickentest.domain.dao.Transaction;

import com.accenture.chickentest.domain.dto.TransactionDTO;
import com.accenture.chickentest.mapper.ModelMapper;
import com.accenture.chickentest.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.*;
import java.util.stream.Collectors;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class TransactionServiceTest {



    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    private Transaction transaction;
    private Transaction transaction2;
    private Transaction transaction3;

    private List<Transaction> listtransactions ;


    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        transactionService=new TransactionService(transactionRepository);
        transaction=new Transaction();
        transaction.setId(1L);
        transaction.setPrice(14);
        transaction.setDateTransaction(new Date());
        transaction.setTypeTransaction("Compra");
        transaction.setTypeProduct("Chicken");

        transaction2=new Transaction();
        transaction2.setId(2L);
        transaction2.setPrice(12);
        transaction2.setDateTransaction(new Date());
        transaction2.setTypeTransaction("Venta");
        transaction2.setTypeProduct("Egg");

        transaction3=new Transaction();
        transaction3.setId(3L);
        transaction3.setPrice(12);
        transaction3.setDateTransaction(new Date());
        transaction3.setTypeTransaction("Venta");
        transaction3.setTypeProduct("Chicken");

        listtransactions = new ArrayList<Transaction>();


        listtransactions.add(transaction);
        listtransactions.add(transaction2);
        listtransactions.add(transaction3);
    }




    @Test
    void getTransactionsBuys() {

        when(transactionRepository.findAll()).thenReturn(listtransactions);
        assertEquals(1,transactionService.getTransactionsBuys().stream().count());



    }

    @Test
    void getTransactionsSells() {


        when(transactionRepository.findAll()).thenReturn(listtransactions);
        assertEquals(2,transactionService.getTransactionsSells().stream().count());



    }

    @Test
    void getAmountBuys() {


        when(transactionRepository.findAll()).thenReturn(listtransactions);
        assertEquals(14,transactionService.getAmountBuys());
        assertNotEquals(300,transactionService.getAmountBuys());

    }

    @Test
    void getAmountSells() {

        when(transactionRepository.findAll()).thenReturn(listtransactions);
        assertNotEquals(455,transactionService.getAmountSells());
        assertEquals(24,transactionService.getAmountSells());
    }

    @Test
    void getNumberTransactions() {
        when(transactionRepository.findAll()).thenReturn(listtransactions);
        assertNotEquals(3,transactionService.getNumberTransactions("Compra","Chicken"));
        assertEquals(1,transactionService.getNumberTransactions("Compra","Chicken"));
        assertNotEquals(4,transactionService.getNumberTransactions("Venta","Egg"));
        assertEquals(1,transactionService.getNumberTransactions("Venta","Egg"));

    }

    @Test
    void getTransactions() {
        when(transactionRepository.findAll()).thenReturn(listtransactions);
        assertEquals(3,transactionService.getTransactions().stream().count());
        assertNotEquals(2,transactionService.getTransactions().stream().count());

    }
}