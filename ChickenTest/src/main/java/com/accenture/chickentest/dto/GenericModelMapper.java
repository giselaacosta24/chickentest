package com.accenture.chickentest.dto;

import com.accenture.chickentest.domain.model.Chicken;
import org.modelmapper.ModelMapper;

public class GenericModelMapper {
    private final ModelMapper mapper=new ModelMapper();
    private static GenericModelMapper instance;
    private GenericModelMapper(){}

    public static GenericModelMapper singleInstance(){
        if(instance == null)
        {
            instance=new GenericModelMapper();
        }
        return  instance;

    }


    public  ChickenDTO mapToChickenDTO(Chicken chicken) {
        return mapper.map(chicken, ChickenDTO.class);
    }
}

