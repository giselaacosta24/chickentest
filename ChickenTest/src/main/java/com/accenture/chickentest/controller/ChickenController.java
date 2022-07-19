package com.accenture.chickentest.controller;


import com.accenture.chickentest.service.ChickenService;
import com.accenture.chickentest.domain.dto.ChickenDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chickens")
public class ChickenController {

    @Autowired
    private ChickenService chickenService;


    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ChickenDTO>  getAllChickens() {

        return chickenService.getChickens();
    }

    @PostMapping
    public ResponseEntity<ChickenDTO> createChicken(@RequestBody ChickenDTO chickenDTO) {

       return chickenService.save(chickenDTO);
    }

  @PutMapping("/{id}")
   public ResponseEntity<ChickenDTO> updateChicken(@PathVariable long id, @RequestBody ChickenDTO chickenDTO) {

      return chickenService.update(id,chickenDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteChicken(@PathVariable(name = "id") Long id) {
        chickenService.deleteChicken(id);
        return "Chicken eliminado";
   }

}
