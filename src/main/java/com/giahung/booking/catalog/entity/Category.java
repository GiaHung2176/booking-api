package com.giahung.booking.catalog.entity;

import jakarta.persistence.*;

@Entity
public class Category{
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false,length=100)
    private String name;

    protected Category(){}

    public Category(String name){
        this.name=name;
    }
    public Long getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }

}   