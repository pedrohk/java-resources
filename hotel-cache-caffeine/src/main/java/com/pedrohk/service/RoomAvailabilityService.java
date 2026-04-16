package com.pedrohk.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class RoomAvailabilityService {

    @Cacheable(value = "roomAvailability")
    public String checkAvailability(int roomNumber) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Room " + roomNumber + " is available";
    }
}
