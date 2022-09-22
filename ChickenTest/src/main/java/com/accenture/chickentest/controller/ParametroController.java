package com.accenture.chickentest.controller;

import com.accenture.chickentest.domain.dto.ParametroDTO;
import com.accenture.chickentest.service.ParametroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin({"http://localhost:4200"})
@RequestMapping("/parametros")
public class ParametroController {

    @Autowired
    private ParametroService parametroService;

    @PostMapping
    public ResponseEntity<ParametroDTO> createParametro(@RequestBody ParametroDTO parametroDTO) {

        return parametroService.save(parametroDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParametroDTO> updateParametro(@PathVariable long id,@RequestBody ParametroDTO parametroDTO) {

        return parametroService.update(id,parametroDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ParametroDTO>  deleteParametro(@PathVariable(name = "id") Long id) {
        ;
        return parametroService.delete(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ParametroDTO> getAllParametros() {

        return parametroService.getParametros();
    }
    @GetMapping("/{id}")
    public ParametroDTO getParametro(@PathVariable long id) {

        return parametroService.getParametro(id);
    }
}
