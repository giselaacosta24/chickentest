package com.accenture.chickentest.domain.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @JsonProperty("typeProduct")
    private String typeProduct;



    @JsonProperty("typeTransaction")
    private String typeTransaction;

    @JsonProperty("price")
    private double price;


    @JsonProperty("dateTransaction")
   private Date dateTransaction;

    public Transaction() {

    }

    public Transaction(Long id, String typeProduct, String typeTransaction, double price, Date dateTransaction) {
        this.id = id;
        this.typeProduct = typeProduct;
        this.typeTransaction = typeTransaction;
        this.price = price;
        this.dateTransaction = dateTransaction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeProduct() {
        return typeProduct;
    }

    public void setTypeProduct(String typeProduct) {
        this.typeProduct = typeProduct;
    }

    public String getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(String typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(Date dateTransaction) {
        this.dateTransaction = dateTransaction;
    }
}
