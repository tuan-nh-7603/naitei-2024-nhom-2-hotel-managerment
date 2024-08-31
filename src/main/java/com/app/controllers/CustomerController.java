package com.app.controllers;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
@Slf4j
@RequiredArgsConstructor
public class CustomerController {
    @GetMapping("/rooms")
    public String dashboard() {
        return "customer/rooms";
    }
}
