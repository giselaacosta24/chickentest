package com.accenture.chickentest.domain.service;

import com.accenture.chickentest.domain.model.Egg;
import com.accenture.chickentest.domain.repository.EggRepository;
import com.accenture.chickentest.dto.EggDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EggService {

    @Autowired
    private EggRepository rep;

   // @Value(value = "${variable.test}")
   // private String cantdias;
    public List<EggDTO> getEggs() {
        return rep.findAll().stream().map(EggDTO::create).collect(Collectors.toList());
    }

    public Egg save(Egg egg) {

       return rep.save(egg);
    }

    public void delete(Long id) {

        rep.deleteById(id);
    }




}
