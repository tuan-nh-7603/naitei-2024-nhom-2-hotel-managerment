package com.app.repositories;

import com.app.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Custom query to find bookings by hotel staff
    List<Booking> findByBookingRooms_Room_Hotel_Id(Integer hotelStaffId);
}
