package com.app.services.impl;

import com.app.models.Booking;
import com.app.models.User;
import com.app.repositories.BookingRepository;
import com.app.services.BookingService;
import com.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserService userService;

    @Override
    public int countBookings() {
        return (int) bookingRepository.count();
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking findById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    @Override
    public List<Booking> findBookingsByHotelStaff() {
        // Get the current logged-in user
        User currentUser = userService.getCurrentUser();
        // Assuming each staff user is associated with one or more hotels
        return bookingRepository.findByBookingRooms_Room_Hotel_Id(currentUser.getId());
    }

    @Override
    public void save(Booking booking) {
        bookingRepository.save(booking);
    }

    @Override
    public void update(Long id, Booking booking) {
        Booking existingBooking = findById(id);
    }

    @Override
    public void delete(Long id) {
        bookingRepository.deleteById(id);
    }
}

