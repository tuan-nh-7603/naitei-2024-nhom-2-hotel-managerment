package com.app.controllers;

import com.app.models.Booking;
import com.app.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookingsController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/admin/bookings")
    public String listBookings(Model model) {
        model.addAttribute("bookings", bookingService.findAll());
        return "/admin/bookings";
    }

    @GetMapping("/manager/bookings")
    public String listBookingsForHotelStaff(Model model) {
        // Fetch bookings for the hotels managed by the logged-in hotel staff
        // You would typically pass the hotel staff's ID or username here
        model.addAttribute("bookings", bookingService.findBookingsByHotelStaff());
        return "/hotelmanager/bookings";
    }

    @GetMapping("/manager/bookings/edit/{id}")
    public String showEditBookingForm(@PathVariable Long id, Model model) {
        Booking booking = bookingService.findById(id);
        model.addAttribute("booking", booking);
        return "/hotelmanager/edit-booking";
    }

    @PostMapping("/manager/bookings/update/{id}")
    public String updateBooking(@PathVariable Long id, Booking booking) {
        bookingService.update(id, booking);
        return "redirect:/hotelmanager/bookings";
    }

    @GetMapping("/manager/bookings/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingService.delete(id);
        return "redirect:/hotelmanager/bookings";
    }
}

