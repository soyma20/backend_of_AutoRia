package com.example.backend_of_autoria.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String brand;

    private  String model;

    private int year;

    private int price;

    private String description;

    @ManyToOne
    @JoinTable(
            name = "user_cars",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns  = @JoinColumn(name = "user_id")
    )
    private Customer owner;


}
