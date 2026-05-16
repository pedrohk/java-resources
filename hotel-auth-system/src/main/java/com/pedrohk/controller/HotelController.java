package com.pedrohk.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class HotelController {

    @GetMapping("/public/info")
    public Map<String, String> getHotelInfo() {
        return Map.of("name", "Pedro Palace", "location", "Porto Alegre");
    }

    @GetMapping("/guest/my-room")
    public Map<String, String> getGuestData() {
        return Map.of("room", "101", "status", "Occupied");
    }

    @GetMapping("/manager/reports")
    public Map<String, String> getReports() {
        return Map.of("daily_revenue", "5000.00", "occupancy_rate", "85%");
    }
}
