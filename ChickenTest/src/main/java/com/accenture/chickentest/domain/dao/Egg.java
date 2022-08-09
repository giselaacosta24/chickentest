package com.accenture.chickentest.domain.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Egg {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;
    @JsonProperty("price")
    private double price;

    @JsonProperty("amountDays")
    private Long amountDays;

    @JsonProperty("idFarm")
    private Long idFarm;

    @JsonProperty("status")
    private eggStatus status;
    public enum eggStatus {
        NACIDO,
        COMPRADO,
        VENDIDO,
        CONVERTIDO;
    }



    public Egg(){}

    public Egg(Long id, double price, Long amountDays, Long idFarm, eggStatus status) {
        this.id = id;
        this.price = price;
        this.amountDays = amountDays;
        this.idFarm = idFarm;
        this.status = status;
    }

    public Egg(Long id, double price, Long amountDays, Long idFarm) {
        this.id = id;
        this.price = price;
        this.amountDays = amountDays;
        this.idFarm = idFarm;
    }

    public Long getIdFarm() {
        return idFarm;
    }

    public void setIdFarm(Long idFarm) {
        this.idFarm = idFarm;
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


    public Long getAmountDays() {
        return amountDays;
    }

    public void setAmountDays(Long amountDays) {
        this.amountDays = amountDays;
    }

    public void setStatus(eggStatus status) {
        this.status = status;
    }

    public eggStatus getStatus() {
        return status;
    }


}

