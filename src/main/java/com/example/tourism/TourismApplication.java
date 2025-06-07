package com.example.tourism;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.example.tourism.model")
@EnableJpaRepositories("com.example.tourism.repository")
public class TourismApplication {
    public static void main(String[] args) {
        SpringApplication.run(TourismApplication.class, args);
    }
}