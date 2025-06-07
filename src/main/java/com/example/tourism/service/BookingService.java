package com.example.tourism.service;

import com.example.tourism.model.Booking;
import com.example.tourism.model.Tour;
import com.example.tourism.model.User;
import com.example.tourism.repository.BookingRepository;
import com.example.tourism.repository.TourRepository;
import com.example.tourism.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TourRepository tourRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Transactional
    public Booking createBooking(Long tourId, String username, Integer numberOfPeople) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Тур не найден"));

        if (numberOfPeople > tour.getMaxParticipants()) {
            throw new RuntimeException("Превышено максимальное количество участников");
        }

        Booking booking = new Booking();
        booking.setTour(tour);
        booking.setUser(user);
        booking.setBookingDate(LocalDate.now());
        booking.setNumberOfPeople(numberOfPeople);
        booking.setTotalPrice(tour.getPrice().multiply(BigDecimal.valueOf(numberOfPeople)));
        booking.setStatus("Забронировано");

        return bookingRepository.save(booking);
    }
} 