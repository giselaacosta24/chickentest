package com.accenture.chickentest.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


import lombok.Data;


@Data
public class FarmDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;


    @JsonProperty("estimate")
    private double estimate;

 public FarmDTO() {

 }
    public FarmDTO(Long id, String name, double estimate) {
        this.id = id;
        this.name = name;
        this.estimate = estimate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public double getEstimate() {
        return estimate;
    }

    public void setEstimate(double estimate) {
        this.estimate = estimate;
    }




}


