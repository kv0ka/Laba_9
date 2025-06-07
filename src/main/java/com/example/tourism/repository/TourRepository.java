package com.example.tourism.repository;

import com.example.tourism.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {
    List<Tour> findByStartDateAfter(LocalDate date);
    List<Tour> findByDestinationId(Long destinationId);
}