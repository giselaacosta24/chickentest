package com.accenture.chickentest.domain.model;

import lombok.*;

import javax.persistence.*;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chickens", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class Chicken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price")
    private double price;

    @Column(name = "idFarmer")
    private Long idFarmer;

    @Column(name = "amountDays")
    private Long amountDays;


}