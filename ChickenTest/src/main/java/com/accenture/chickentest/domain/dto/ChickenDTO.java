package com.accenture.chickentest.domain.dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class ChickenDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("price")
    private double price;

    @JsonProperty("amountDays")
    private Long amountDays;
public ChickenDTO(){}
    public ChickenDTO(Long id, double price, Long amountDays) {
        this.id = id;
        this.price = price;
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

    public Long getAmountDays() {
        return amountDays;
    }

    public void setAmountDays(Long amountDays) {
        this.amountDays = amountDays;
    }
}
