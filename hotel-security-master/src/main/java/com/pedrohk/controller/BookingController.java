package com.pedrohk.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    @GetMapping
    public Map<String, String> getBookings() {
        return Map.of("message", "List of all active hotel bookings");
    }

    @PostMapping
    public Map<String, String> createBooking(@RequestBody Map<String, String> payload) {
        return Map.of(
                "status", "Success",
                "guest", payload.get("guestName"),
                "message", "Secure booking created with CSRF protection"
        );
    }
}
