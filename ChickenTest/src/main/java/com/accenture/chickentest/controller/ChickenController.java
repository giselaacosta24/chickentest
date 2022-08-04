package com.accenture.chickentest.controller;


import com.accenture.chickentest.service.ChickenService;
import com.accenture.chickentest.domain.dto.ChickenDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin ({"http://localhost:4200"})
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
    @GetMapping("/{id}")
    public ChickenDTO getChicken(@PathVariable long id) {

        return chickenService.getChicken(id);
    }

    @GetMapping("/chickensfree")
    public List<ChickenDTO> getChickensWithOutFarm() {

        return chickenService.getChickensWithOutFarm();
    }
    @GetMapping("/chickensFarm/{id}")
    public List<ChickenDTO> getChickensWithFarm(@PathVariable long id) {

        return chickenService.getChickensWithFarm(id);
    }
  @PutMapping("/{id}")
   public ResponseEntity<ChickenDTO> updateChicken(@PathVariable long id, @RequestBody ChickenDTO chickenDTO) {

      return chickenService.update(id,chickenDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ChickenDTO>  deleteChicken(@PathVariable(name = "id") Long id) {
        ;
        return chickenService.deleteChicken(id);
   }

}
