package com.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookingRoomDTO {
    private Integer id;
    private Integer bookingId;
    private Integer roomId;
    private Double currentPricePerNight;

}

