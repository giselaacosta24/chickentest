package com.accenture.chickentest.api;

import com.accenture.chickentest.domain.model.Egg;
import com.accenture.chickentest.domain.service.EggService;
import com.accenture.chickentest.dto.EggDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/eggs")
public class EggsController {
    @Autowired
    private EggService service;

    @GetMapping()
    public ResponseEntity get() {
        List<EggDTO> eggs = service.getEggs();
        return ResponseEntity.ok(eggs);
    }


    @PostMapping
    public String post(@RequestBody Egg egg) {

    Egg e=  service.save(egg);
    return "Guardado:"+ e.getId();
    }



    @DeleteMapping ("/{id}")
    public String delete(@PathVariable("id") Long id) {
        service.delete(id);
        return "Egg eliminado";
    }









}
