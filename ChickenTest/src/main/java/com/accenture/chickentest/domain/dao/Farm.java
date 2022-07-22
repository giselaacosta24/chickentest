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
/*    @Transient
    private List<Chicken> chickens;
    @Transient
    private List<Egg> eggs;*/

    @JsonProperty("estimate")
    private double estimate;
    public Farm(){
//        this.eggs=new ArrayList<>();
//        this.chickens=new ArrayList<>();
    }
/*    public Farm(Long id, String name, List<Chicken> chickens, List<Egg> eggs, double estimate) {
        this.id = id;
        this.name = name;
        this.chickens = chickens;
        this.eggs = eggs;
        this.estimate = estimate;
    }*/

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

 /*   public List<Chicken> getChickens() {
        return chickens;
    }

    public void setChickens(List<Chicken> chickens) {
        this.chickens = chickens;
    }

    public List<Egg> getEggs() {
        return eggs;
    }

    public void setEggs(List<Egg> eggs) {
        this.eggs = eggs;
    }
*/
    public double getEstimate() {
        return estimate;
    }

    public void setEstimate(double estimate) {
        this.estimate = estimate;
    }
}



