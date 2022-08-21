package com.accenture.chickentest.mapper;

import com.accenture.chickentest.domain.dao.*;
import com.accenture.chickentest.domain.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper
public interface ModelMapper {

    ModelMapper INSTANCE = Mappers.getMapper(ModelMapper.class);


    public ChickenDTO daoToDTOChicken(Chicken chicken);
    public Chicken DTOtoDaoChicken(ChickenDTO chicken);
    public EggDTO daoToDTOEgg(Egg egg);
    public Egg DTOtoDaoEgg(EggDTO egg);

    public FarmDTO daoToDTOFarm(Farm farm);
    public Farm DTOtoDaoFarm(FarmDTO farm);

    public TransactionDTO daoToDTOTransaction(Transaction transaction);
    public Transaction DTOtoDaoTransaction(TransactionDTO transactionDTO);


    public ParametroDTO daoToDTOParametro(Parametro parametro);
    public Parametro DTOtoDaoParametro(ParametroDTO parametroDTO);
}
