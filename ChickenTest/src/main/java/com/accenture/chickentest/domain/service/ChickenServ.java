package com.accenture.chickentest.domain.service;

import com.accenture.chickentest.domain.model.Chicken;

import java.util.List;

public interface ChickenServ {
    List<Chicken> getAllChickens();

    Chicken createChicken(Chicken chicken);

 /*   Chicken updateChicken(long id, Chicken chicken);

    void deleteChicken(long id);

    Chicken getChickenById(long id);
*/
}