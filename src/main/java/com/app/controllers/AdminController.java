package com.app.controllers;

import com.app.services.BookingService;
import com.app.services.HotelService;
import com.app.services.UserService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    @Autowired
    BookingService bookingService;
    @Autowired
    UserService userService;
    @Autowired
    HotelService hotelService;

    @GetMapping("/users")
    public String users(){return "admin/users";}

    @GetMapping("/admin/dashboard")
    public String showDashboard(Model model) {
        // Retrieve counts from the service layer
        int bookingCount = bookingService.countBookings();
        int hotelCount = hotelService.countHotels();
        int userCount = userService.countUsers();

        // Add attributes to the model to be used in the Thymeleaf template
        model.addAttribute("bookingCount", bookingCount);
        model.addAttribute("hotelCount", hotelCount);
        model.addAttribute("userCount", userCount);

        return "admin/dashboard"; // the name of the Thymeleaf template
    }



}
