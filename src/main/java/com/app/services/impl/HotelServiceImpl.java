package com.app.services.impl;

import com.app.repositories.HotelRepository;
import com.app.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    HotelRepository hotelRepository;

    @Override
    public int countHotels() {
        return (int) hotelRepository.count();
    }
}
