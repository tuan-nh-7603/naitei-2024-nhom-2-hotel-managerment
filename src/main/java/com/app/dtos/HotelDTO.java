package com.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HotelDTO {
    private Integer id;
    private String name;
    private String location;
    private String description;
    private String contactInfo;

}

