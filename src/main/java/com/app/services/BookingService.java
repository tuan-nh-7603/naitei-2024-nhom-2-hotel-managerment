package com.app.services;

import com.app.models.Booking;
import com.app.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService {

    public int countBookings();

    public List<Booking> findAll() ;

    public Booking findById(Long id) ;

    public List<Booking> findBookingsByHotelStaff() ;

    public void save(Booking booking) ;

    public void update(Long id, Booking booking) ;
    public void delete(Long id) ;
}
