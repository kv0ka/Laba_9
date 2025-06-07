package com.example.tourism.controller;

import com.example.tourism.model.User;
import com.example.tourism.model.Booking;
import com.example.tourism.model.Tour;
import com.example.tourism.service.UserService;
import com.example.tourism.service.BookingService;
import com.example.tourism.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private TourService tourService;

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/users";
    }

    @PostMapping("/users/add")
    public String addUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            userService.createUser(user);
            redirectAttributes.addFlashAttribute("success", "Пользователь успешно добавлен");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при добавлении пользователя: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/users/edit")
    public String editUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            userService.updateUser(user);
            redirectAttributes.addFlashAttribute("success", "Пользователь успешно обновлен");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении пользователя: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/users/delete")
    public String deleteUser(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.softDeleteUser(id);
            redirectAttributes.addFlashAttribute("success", "Пользователь успешно удален");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении пользователя: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/bookings")
    public String listBookings(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        List<User> users = userService.getAllUsers();
        List<Tour> tours = tourService.getAllTours();
        model.addAttribute("bookings", bookings);
        model.addAttribute("users", users);
        model.addAttribute("tours", tours);
        return "admin/bookings";
    }

    @PostMapping("/bookings/add")
    public String addBooking(@RequestParam Long userId,
                           @RequestParam Long tourId,
                           @RequestParam int numberOfPeople,
                           RedirectAttributes redirectAttributes) {
        try {
            bookingService.createBooking(userId, tourId, numberOfPeople);
            redirectAttributes.addFlashAttribute("success", "Бронирование успешно добавлено");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при добавлении бронирования: " + e.getMessage());
        }
        return "redirect:/admin/bookings";
    }

    @PostMapping("/bookings/edit")
    public String editBooking(@RequestParam Long id,
                            @RequestParam Long userId,
                            @RequestParam Long tourId,
                            @RequestParam int numberOfPeople,
                            RedirectAttributes redirectAttributes) {
        try {
            bookingService.updateBooking(id, userId, tourId, numberOfPeople);
            redirectAttributes.addFlashAttribute("success", "Бронирование успешно обновлено");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении бронирования: " + e.getMessage());
        }
        return "redirect:/admin/bookings";
    }

    @PostMapping("/bookings/delete")
    public String deleteBooking(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            bookingService.deleteBooking(id);
            redirectAttributes.addFlashAttribute("success", "Бронирование успешно удалено");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении бронирования: " + e.getMessage());
        }
        return "redirect:/admin/bookings";
    }
} 