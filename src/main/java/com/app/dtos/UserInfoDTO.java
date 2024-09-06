package com.app.dtos;

import com.app.constants.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDTO {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Role role;
    private Integer hotelId;  // For associating a user with a hotel, if needed
}
