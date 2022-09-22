package com.accenture.chickentest.controller;


import com.accenture.chickentest.domain.dto.TransactionDTO;
import com.accenture.chickentest.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")

public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/buys")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionDTO> getAllTransactionsBuys(){


        return transactionService.getTransactionsBuys();
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionDTO> getAllTransactions(){


        return transactionService.getTransactions();
    }


    @GetMapping("/buys/amount")
    @ResponseStatus(HttpStatus.OK)
    public double getAllAmountBuys(){


        return transactionService.getAmountBuys();
    }


    @GetMapping("/sells")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionDTO> getAllTransactionsSells(){


        return transactionService.getTransactionsSells();
    }

    @GetMapping("/sells/amount")
    @ResponseStatus(HttpStatus.OK)
    public double getAllAmountSells(){


        return transactionService.getAmountSells();
    }




    @GetMapping("/{typetransaccion}/{typeproduct}")
    @ResponseStatus(HttpStatus.OK)
    public int getAllNumbers(@PathVariable String typetransaccion,@PathVariable String typeproduct){


        return transactionService.getNumberTransactions(typetransaccion,typeproduct);
    }



}
