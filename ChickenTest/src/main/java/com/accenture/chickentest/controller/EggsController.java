package com.accenture.chickentest.controller;


import com.accenture.chickentest.service.EggService;
import com.accenture.chickentest.domain.dto.EggDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/eggs")
public class EggsController {

    @Autowired
    private EggService eggService;


    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<EggDTO>  getAllEggs() {

        return eggService.getEggs();
    }

    @PostMapping
    public ResponseEntity<EggDTO> createEgg(@RequestBody EggDTO eggDTO) {

        return eggService.save(eggDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EggDTO> updateEgg(@PathVariable long id, @RequestBody EggDTO eggDTO) {

        return eggService.update(id,eggDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteEgg(@PathVariable(name = "id") Long id) {
        eggService.deleteEgg(id);
        return "Egg eliminado";
    }


}
