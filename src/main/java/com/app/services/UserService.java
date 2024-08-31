package com.app.services;

import com.app.dtos.ResetPasswordDTO;
import com.app.dtos.UserDTO;
import com.app.dtos.UserRegistrationDTO;
import com.app.models.User;

import java.util.List;

public interface UserService {
    User saveUser(UserRegistrationDTO registrationDTO);

    User findUserByEmail(String email);

    void updateLoggedInUser(UserDTO userDTO);

}
