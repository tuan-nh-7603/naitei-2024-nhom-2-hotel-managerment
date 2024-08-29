package com.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookingDTO {
    private Integer id;
    private Integer customerId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private String status;
    private Double totalAmount;
    private LocalDateTime createdAt;
    private List<BookingRoomDTO> bookingRooms;

}

