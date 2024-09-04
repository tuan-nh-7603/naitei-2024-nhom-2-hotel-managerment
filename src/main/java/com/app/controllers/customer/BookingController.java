package com.app.controllers.customer;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
@Slf4j
@RequiredArgsConstructor
public class BookingController {
    @GetMapping("/hotels")
    public String dashboard() {
        return "customer/hotels";
    }
}
