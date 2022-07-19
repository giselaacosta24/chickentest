package com.accenture.chickentest.mapper;

import com.accenture.chickentest.domain.dao.Chicken;
import com.accenture.chickentest.domain.dao.Egg;
import com.accenture.chickentest.domain.dto.ChickenDTO;
import com.accenture.chickentest.domain.dto.EggDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface ModelMapper {

    ModelMapper INSTANCE = Mappers.getMapper(ModelMapper.class);


    public ChickenDTO daoToDTOChicken(Chicken chicken);
    public Chicken DTOtoDaoChicken(ChickenDTO chicken);
    public EggDTO daoToDTOEgg(Egg egg);
    public Egg DTOtoDaoEgg(EggDTO egg);

}
