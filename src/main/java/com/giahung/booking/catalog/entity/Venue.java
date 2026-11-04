package com.giahung.booking.catalog.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity 
public class Venue {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false,length = 255)
    private String name;

    @Column (nullable = false,length = 255)
    private String address;

    @Column (nullable = false,length = 100)
    private String city;
    
    @Column (nullable = false,updatable = false)
    private Instant createdAt;
    
    @Column (nullable = false)
    private Instant updatedAt;

    protected Venue(){}

    public Venue(String name,String address,String city){
        this.name=name;
        this.address=address;
        this.city=city;
        createdAt=Instant.now();
        updatedAt=createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void update(String name,String address,String city){
        this.name=name;
        this.address=address;
        this.city=city;
        updatedAt=Instant.now();
    }

}
