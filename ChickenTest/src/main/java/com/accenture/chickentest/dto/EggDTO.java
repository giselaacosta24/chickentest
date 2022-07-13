package com.accenture.chickentest.dto;

import com.accenture.chickentest.domain.model.Egg;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class EggDTO {
    private Long id;
    private double price;
    private Long idFarmer;
    private Long amountDays;

//mapeo singleton
    public static EggDTO create(Egg egg) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(egg, EggDTO.class);
    }
}
