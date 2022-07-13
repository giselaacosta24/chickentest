package com.accenture.chickentest.api;


import com.accenture.chickentest.domain.model.Chicken;
import java.util.stream.Collectors;

import com.accenture.chickentest.domain.service.ChickenService;
import com.accenture.chickentest.dto.ChickenDTO;
import org.modelmapper.ModelMapper;
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
    private ModelMapper modelMapper;

    public ChickenController(ChickenService chickenService) {
        super();
        this.chickenService = chickenService;
    }
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ChickenDTO>  getAllChickens() {

        return chickenService.getAllChickens().stream().map(chicken -> modelMapper.map(chicken, ChickenDTO.class))
                .collect(Collectors.toList());
    }
    @PostMapping
    public ResponseEntity<ChickenDTO> createChicken(@RequestBody ChickenDTO chickenDTO) {

        // convert DTO to entity
        Chicken chickenRequest = modelMapper.map(chickenDTO, Chicken.class);

        Chicken chicken = chickenService.createChicken(chickenRequest);

        // convert entity to DTO
        ChickenDTO chickenResponse = modelMapper.map(chicken, ChickenDTO.class);

        return new ResponseEntity<>(chickenResponse, HttpStatus.CREATED);
    }











}
