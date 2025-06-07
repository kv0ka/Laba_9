package com.example.tourism.repository;

import com.example.tourism.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    List<Booking> findByTourId(Long tourId);
    List<Booking> findByStatus(String status);
}