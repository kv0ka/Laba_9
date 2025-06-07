package com.example.tourism.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Destination destination;

    private Integer rating;
    private BigDecimal pricePerNight;
    private String description;
    private String amenities;
}