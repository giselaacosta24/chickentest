package com.accenture.chickentest.service;

import com.accenture.chickentest.domain.dto.FarmDTO;
import com.accenture.chickentest.mapper.ModelMapper;

import com.accenture.chickentest.repository.FarmRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class FarmService {

    private final FarmRepository farmRepository;
    public FarmService(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }
    public List<FarmDTO> getFarm()
    {
        return farmRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOFarm)
                .collect(Collectors.toList());

    }
    //transformar huevo a pollo

    //mostrar informacion granja completa
}
