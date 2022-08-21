package com.accenture.chickentest.controller;

import com.accenture.chickentest.domain.dto.FarmDTO;
import com.accenture.chickentest.service.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin({"http://localhost:4200"})
@RequestMapping("/api/v1/farms")

public class FarmController {
    @Autowired
    private FarmService farmService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FarmDTO> getAllFarms(){


        return farmService.getFarms();
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FarmDTO getFarm(@PathVariable Long id){

        return farmService.getFarm(id);
    }
    @GetMapping("/getFarmByName/{name}")
    @ResponseStatus(HttpStatus.OK)
    public FarmDTO getFarmByName(@PathVariable(name = "name") String name){

        return farmService.getFarmIdByName(name);
    }
    @PostMapping
    public ResponseEntity<FarmDTO> createFarm(@RequestBody FarmDTO farmDTO) {

        return farmService.save(farmDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FarmDTO> updateFarm(@PathVariable long id, @RequestBody FarmDTO farmDTO) {

        return farmService.update(id,farmDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FarmDTO>  deleteFarm(@PathVariable(name = "id") Long id) {
        return  farmService.deleteFarm(id);
    }

    @PutMapping("/{tipo}/{id}/{number}")
    public ResponseEntity<FarmDTO> updateAmount(@PathVariable String tipo,@PathVariable long id,@PathVariable double number, @RequestBody FarmDTO farmDTO) {
        {

            return this.farmService.updateAmount(tipo,id,farmDTO,number);

        }
    }




}
