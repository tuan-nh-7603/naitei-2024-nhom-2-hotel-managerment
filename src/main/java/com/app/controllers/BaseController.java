package com.app.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import com.app.services.UserService;
import com.app.dtos.UserRegistrationDTO;
import com.app.exceptions.UsernameAlreadyExistsException;
import com.app.security.RedirectUtil;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BaseController {
    protected final UserService userService;

    protected String registerUser(@Valid UserRegistrationDTO registrationDTO, BindingResult result, String view, String redirectUrl) {
        if (result.hasErrors()) {
            return view;
        }
        try {
            log.info("Registering user: {}", registrationDTO.getEmail());
            userService.saveUser(registrationDTO);
        } catch (UsernameAlreadyExistsException e) {
            result.rejectValue("email", "user.exists", e.getMessage());
            return view;
        }
        return "redirect:/" + redirectUrl + "?success";
    }

    protected String getAuthenticatedUserRedirectUrl(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String redirectUrl = RedirectUtil.getRedirectUrl(authentication);
            if (redirectUrl != null) {
                return "redirect:" + redirectUrl;
            }
        }
        return null;
    }
}
