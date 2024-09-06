package com.app.services;

import com.app.models.Booking;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService {

    public int countBookings();

    public List<Booking> findAll() ;

    public Booking findById(Integer id) ;

    public List<Booking> findBookingsByHotelStaff() ;

    public void save(Booking booking) ;

    public void update(Integer id, Booking booking) ;
    public void delete(Integer id) ;
}
