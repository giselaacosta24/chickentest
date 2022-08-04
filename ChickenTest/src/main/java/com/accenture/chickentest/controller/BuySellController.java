package com.accenture.chickentest.controller;

import com.accenture.chickentest.domain.dao.Chicken;
import com.accenture.chickentest.domain.dto.ChickenDTO;
import com.accenture.chickentest.domain.dto.FarmDTO;
import com.accenture.chickentest.service.BuySellService;
import com.accenture.chickentest.service.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin({"http://localhost:4200"})
@RequestMapping("/api/v1/chickentest")

public class BuySellController {
    @Autowired
    private BuySellService buySellService;


    @PutMapping("/{id}")
    public ResponseEntity<ChickenDTO> comprarChickens(@RequestBody ChickenDTO chickenDTO, @PathVariable long id)
    {

      return this.buySellService.buyChicken(chickenDTO,id);

    }

 /*   @PutMapping("/{id}")
    public ResponseEntity<FarmDTO> updateAmount(@RequestBody FarmDTO farmDTO, @PathVariable long id)
    {

        return this.buySellService.updateAmount(farmDTO,id);

    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<ChickenDTO> venderChickens(@PathVariable(name = "id") Long id)
    {

        return this.buySellService.sellChicken(id);

    }


}
