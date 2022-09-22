package com.accenture.chickentest.controller;


import com.accenture.chickentest.domain.dto.ChickenDTO;
import com.accenture.chickentest.domain.dto.EggDTO;
import com.accenture.chickentest.service.TransformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transformation")
public class TransformationController {
    @Autowired
    private TransformationService transformationService;

    @PutMapping("/eggs")
    public ResponseEntity<EggDTO> eggToChicken( )
    {

        return this.transformationService.eggToChicken();

    }

    @PutMapping("/chickens")
    public ResponseEntity<ChickenDTO> chickenToDead()
    {

        return this.transformationService.chickenToDead();

    }
    @PutMapping("/putEgg")
    public ResponseEntity<ChickenDTO> putAnEgg()
    {

        return this.transformationService.putAnEgg();

    }


    @PutMapping("/updateDays")
    public void updateDays()
    {

       this.transformationService.updateDays();

    }
}

