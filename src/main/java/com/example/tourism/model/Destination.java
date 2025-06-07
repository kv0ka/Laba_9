package com.example.tourism.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "destinations")
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String country;

    private String description;
    private String climate;
    private String bestTimeToVisit;
}