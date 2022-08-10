package com.accenture.chickentest.controller;


import com.accenture.chickentest.domain.dto.TransactionDTO;
import com.accenture.chickentest.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin({"http://localhost:4200"})
@RequestMapping("/api/v1/transactions")

public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/buys")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionDTO> getAllTransactionsBuys(){


        return transactionService.getTransactionsBuys();
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

}
