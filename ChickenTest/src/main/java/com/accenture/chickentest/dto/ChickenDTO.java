package com.accenture.chickentest.dto;

import com.accenture.chickentest.domain.model.Chicken;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class ChickenDTO {
    private Long id;
    private double price;
    private Long idFarmer;
    private Long amountDays;


}
