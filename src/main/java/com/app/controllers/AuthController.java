package com.app.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.app.constants.Role;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import com.app.dtos.UserRegistrationDTO;
import com.app.security.RedirectUtil;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.app.services.UserService;
import com.app.exceptions.UsernameAlreadyExistsException;


@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    public String showLoginPage(Authentication authentication) {
        String redirect = getAuthenticatedUserRedirectUrl(authentication);
        if (redirect != null) return redirect;
        return "login";
    }

    @GetMapping("/register/customer")
    public String showCustomerRegistrationForm(@ModelAttribute("user") UserRegistrationDTO registrationDTO, Authentication authentication) {
        String redirect = getAuthenticatedUserRedirectUrl(authentication);
        if (redirect != null) return redirect;
        return "register-customer";
    }

    @PostMapping("/register/customer")
    public String registerCustomerAccount(@Valid @ModelAttribute("user") UserRegistrationDTO registrationDTO, BindingResult result) {
        registrationDTO.setRoleType(Role.CUSTOMER);
        return registerUser(registrationDTO, result, "register-customer", "register/customer");
    }

    @GetMapping("/register/manager")
    public String showManagerRegistrationForm(@ModelAttribute("user") UserRegistrationDTO registrationDTO, Authentication authentication) {
        String redirect = getAuthenticatedUserRedirectUrl(authentication);
        if (redirect != null) return redirect;
        return "register-manager";
    }

    @PostMapping("/register/manager")
    public String registerManagerAccount(@Valid @ModelAttribute("user") UserRegistrationDTO registrationDTO, BindingResult result) {
        registrationDTO.setRoleType(Role.HOTEL_STAFF);
        return registerUser(registrationDTO, result, "register-manager", "register/manager");
    }

    private String registerUser(UserRegistrationDTO registrationDTO, BindingResult result, String view, String redirectUrl) {
        if (result.hasErrors()) {
            return view;
        }
        try {
            log.info("test: {}", registrationDTO.getEmail());
            userService.saveUser(registrationDTO);
        } catch (UsernameAlreadyExistsException e) {
            result.rejectValue("email", "user.exists", e.getMessage());
            return view;
        }
        return "redirect:/" + redirectUrl + "?success";
    }

    private String getAuthenticatedUserRedirectUrl(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String redirectUrl = RedirectUtil.getRedirectUrl(authentication);
            if (redirectUrl != null) {
                return "redirect:" + redirectUrl;
            }
        }
        return null;
    }
}
