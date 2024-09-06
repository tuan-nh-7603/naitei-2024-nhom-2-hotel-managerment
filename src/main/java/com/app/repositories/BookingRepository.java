package com.app.repositories;

import com.app.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    // Custom query to find bookings by hotel staff
    List<Booking> findByBookingRooms_Room_Hotel_Id(Integer hotelStaffId);
}
