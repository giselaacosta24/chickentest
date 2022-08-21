package com.accenture.chickentest.domain.dto;


import com.accenture.chickentest.domain.enumStatus.Status;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@Data
public class ChickenDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("price")
    private double price;

    @JsonProperty("amountDays")
    private Long amountDays;

    @JsonProperty("idFarm")
    private Long idFarm;

    @JsonProperty("status")
    private Status status;
    @JsonProperty("sexo")
    private Boolean sexo;


    @JsonProperty("dateFarm")
    private Date dateFarm;


public ChickenDTO(){}


    public ChickenDTO(Long id, double price, Long amountDays, Long idFarm) {
        this.id = id;
        this.price = price;
        this.amountDays = amountDays;
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

    public Long getIdFarm() {
        return idFarm;
    }

    public void setIdFarm(Long idFarm) {
        this.idFarm = idFarm;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Boolean getSexo() {
        return sexo;
    }

    public void setSexo(Boolean sexo) {
        this.sexo = sexo;
    }


    public Date getDateFarm() {
        return dateFarm;
    }

    public void setDateFarm(Date dateFarm) {
        this.dateFarm = dateFarm;
    }
}
