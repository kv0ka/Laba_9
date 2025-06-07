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
    public Booking createBooking(Long userId, Long tourId, int numberOfPeople) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Тур не найден"));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setTour(tour);
        booking.setNumberOfPeople(numberOfPeople);
        booking.setBookingDate(LocalDate.now());
        booking.setTotalPrice(tour.getPrice().multiply(BigDecimal.valueOf(numberOfPeople)));

        return bookingRepository.save(booking);
    }

    @Transactional
    public Booking updateBooking(Long id, Long userId, Long tourId, int numberOfPeople) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Бронирование не найдено"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Тур не найден"));

        booking.setUser(user);
        booking.setTour(tour);
        booking.setNumberOfPeople(numberOfPeople);
        booking.setTotalPrice(tour.getPrice().multiply(BigDecimal.valueOf(numberOfPeople)));

        return bookingRepository.save(booking);
    }

    @Transactional
    public void deleteBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Бронирование не найдено"));
        bookingRepository.delete(booking);
    }

    public List<Booking> getBookingsByUser(User user) {
        return bookingRepository.findByUserId(user.getId());
    }
} 