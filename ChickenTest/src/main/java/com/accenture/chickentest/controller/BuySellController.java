package com.accenture.chickentest.controller;

import com.accenture.chickentest.domain.dto.ChickenDTO;
import com.accenture.chickentest.domain.dto.EggDTO;
import com.accenture.chickentest.service.BuySellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/chickentest")

public class BuySellController {
    @Autowired
    private BuySellService buySellService;


    @PutMapping("/buyChicken/{id}")
    public ResponseEntity<ChickenDTO> comprarChickens(@RequestBody ChickenDTO chickenDTO, @PathVariable long id)
    {
      return this.buySellService.buyChicken(chickenDTO,id);

    }
    @PutMapping("/buyEgg/{id}")
    public ResponseEntity<EggDTO> comprarEggs(@RequestBody EggDTO eggDTO, @PathVariable long id)
    {

        return this.buySellService.buyEgg(eggDTO,id);

    }



    @PutMapping("/sellChicken/{id}")
    public ResponseEntity<ChickenDTO> venderChickens(@RequestBody ChickenDTO chickenDTO,@PathVariable(name = "id") Long id)
    {

        return this.buySellService.sellChicken(id);

    }


    @PutMapping("/sellEgg/{id}")
    public ResponseEntity<EggDTO> venderEggs(@RequestBody EggDTO eggDTO,@PathVariable(name = "id") Long id)
    {

        return this.buySellService.sellEgg(id);

    }


}
