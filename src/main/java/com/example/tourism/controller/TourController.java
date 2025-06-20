package com.example.tourism.controller;

import com.example.tourism.model.User;
import com.example.tourism.service.BookingService;
import com.example.tourism.service.TourService;
import com.example.tourism.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tours")
public class TourController {

    @Autowired
    private TourService tourService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getAllTours(Model model) {
        model.addAttribute("tours", tourService.getAllTours());
        return "tours/index";
    }

    @PostMapping("/book")
    public String bookTour(@RequestParam Long tourId,
                          @RequestParam Integer numberOfPeople,
                          Authentication authentication,
                          RedirectAttributes redirectAttributes) {
        try {
            String username = authentication.getName();
            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
            
            bookingService.createBooking(user.getId(), tourId, numberOfPeople);
            redirectAttributes.addFlashAttribute("success", "Тур успешно забронирован!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при бронировании: " + e.getMessage());
        }
        return "redirect:/tours";
    }
} 