package com.accenture.chickentest.controller;
import com.accenture.chickentest.domain.dao.Chicken;
import com.accenture.chickentest.domain.dao.Farm;
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

/*    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<FarmDTO> getAllFarms() {

        return farmService.getFarm();
    }*/

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FarmDTO> getAllFarms(){


        return farmService.getFarm();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FarmDTO getFarm(@PathVariable Long id){


        return farmService.getFarm(id);
    }

   @PutMapping("/{id}/asignarchickens")
    public List<FarmDTO>  asignarChickens(@RequestBody List<Chicken> chickens,@PathVariable Long id)
    {
        FarmDTO f = this.farmService.getFarm(id);


        chickens.forEach(c ->{
            f.addChicken(c);
        });
        this.farmService.save(f);

        return farmService.getFarm();
    }
}
