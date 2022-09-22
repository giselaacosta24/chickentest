package com.accenture.chickentest.controller;


import com.accenture.chickentest.service.EggService;
import com.accenture.chickentest.domain.dto.EggDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin ({"http://localhost:4200"})
@RequestMapping("/eggs")
public class EggsController {

    @Autowired
    private EggService eggService;
    @Value("${config.balanceador.test}")
    private String balanceadorTest;

    @GetMapping("/balanceador-test")
    public ResponseEntity<?> balanceadorTest() {
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("balanceador", balanceadorTest);
        response.put("eggs", eggService.getEggs());
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<EggDTO>  getAllEggs() {

        return eggService.getEggs();
    }

    @PostMapping
    public ResponseEntity<EggDTO> createEgg(@RequestBody EggDTO eggDTO) {

        return eggService.save(eggDTO);
    }
    @GetMapping("/{id}")
    public EggDTO getEgg(@PathVariable long id) {

        return eggService.getEgg(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<EggDTO> updateEgg(@PathVariable long id, @RequestBody EggDTO eggDTO) {

        return eggService.update(id,eggDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EggDTO>  deleteEgg(@PathVariable(name = "id") Long id) {
        return  eggService.deleteEgg(id);
    }

    @GetMapping("/eggsfree")
    public List<EggDTO> getEggsWithOutFarm() {

        return eggService.getEggsWithOutFarm();
    }
    @GetMapping("/eggsFarm/{id}")
    public List<EggDTO> getEggsWithFarm(@PathVariable long id) {

        return eggService.getEggsWithFarm(id);
    }
}
