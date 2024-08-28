package com.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoomDTO {
    private Integer id;
    private Integer hotelId;
    private String name;
    private String type;
    private Double pricePerNight;
    private String status;
    private String description;

}
