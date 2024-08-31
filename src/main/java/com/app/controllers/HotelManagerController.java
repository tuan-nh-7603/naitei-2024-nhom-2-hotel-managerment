package com.app.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
@Slf4j
public class HotelManagerController {


    @GetMapping("/dashboard")
    public String dashboard() {
        return "hotelmanager/dashboard";
    }


}
