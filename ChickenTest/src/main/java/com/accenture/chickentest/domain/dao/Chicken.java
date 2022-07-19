package com.accenture.chickentest.domain.dao;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Chicken {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;
    @JsonProperty("price")
    private double price;
    @JsonProperty("idFarmer")
    private Long idFarmer;
    @JsonProperty("amountDays")
    private Long amountDays;
    public Chicken(){}
    public Chicken(Long id, double price, Long idFarmer, Long amountDays) {
        this.id = id;
        this.price = price;
        this.idFarmer = idFarmer;
        this.amountDays = amountDays;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getIdFarmer() {
        return idFarmer;
    }

    public void setIdFarmer(Long idFarmer) {
        this.idFarmer = idFarmer;
    }

    public Long getAmountDays() {
        return amountDays;
    }

    public void setAmountDays(Long amountDays) {
        this.amountDays = amountDays;
    }
}