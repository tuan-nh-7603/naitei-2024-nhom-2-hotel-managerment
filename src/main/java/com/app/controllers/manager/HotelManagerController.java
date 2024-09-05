package com.app.controllers.manager;

import com.app.constants.Role;
import com.app.controllers.BaseController;
import com.app.dtos.UserRegistrationDTO;
import com.app.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manager")
@Slf4j
public class HotelManagerController extends BaseController {
    public HotelManagerController(UserService userService) {
        super(userService);
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "manager/dashboard";
    }

    @GetMapping("/users/manager")
    public String showManagerRegistrationForm(@ModelAttribute("user") UserRegistrationDTO registrationDTO, Authentication authentication) {
        String redirect = getAuthenticatedUserRedirectUrl(authentication);
        if (redirect != null) return redirect;
        return "register-manager";
    }

    @PostMapping("/users/manager")
    public String registerManagerAccount(@Valid @ModelAttribute("user") UserRegistrationDTO registrationDTO, BindingResult result) {
        registrationDTO.setRoleType(Role.HOTEL_STAFF);
        return registerUser(registrationDTO, result, "register-manager", "users/manager");
    }
}