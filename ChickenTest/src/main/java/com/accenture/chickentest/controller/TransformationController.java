package com.accenture.chickentest.controller;


import com.accenture.chickentest.domain.dto.ChickenDTO;
import com.accenture.chickentest.domain.dto.EggDTO;
import com.accenture.chickentest.service.TransactionService;
import com.accenture.chickentest.service.TransformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin({"http://localhost:4200"})
@RequestMapping("/api/v1/transformation")
public class TransformationController {
    @Autowired
    private TransformationService transformationService;

    @PutMapping("/eggs/{id}")
    public ResponseEntity<EggDTO> eggToChicken( @PathVariable long id)
    {

        return this.transformationService.eggToChicken(id);

    }

    @PutMapping("/chickens/{id}")
    public ResponseEntity<ChickenDTO> chickenToDead(@PathVariable long id)
    {

        return this.transformationService.chickenToDead(id);

    }
}

