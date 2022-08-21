package com.accenture.chickentest.service;

import com.accenture.chickentest.domain.dao.Transaction;

import com.accenture.chickentest.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;



import static org.junit.jupiter.api.Assertions.*;


class TransactionServiceTest {



    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;


    private Transaction transaction;




    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);



    }




    @Test
    void getAmountBuys() {
    }

    @Test
    void getTransactionsSells() {
        assertNotNull(transactionService.getTransactionsSells());

    }
}