package com.accenture.chickentest.domain.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@Table(name = "farm")
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")

    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("estimate")
    private double estimate;
    public Farm(){

    }


    public Farm(Long id, String name, double estimate) {
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



