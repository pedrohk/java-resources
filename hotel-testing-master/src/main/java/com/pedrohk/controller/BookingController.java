package com.pedrohk.controller;

import com.pedrohk.model.Booking;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking request) {
        if (request.guestName() == null || request.guestName().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Booking confirmed = new Booking(
                UUID.randomUUID().toString(),
                request.guestName(),
                request.roomType(),
                request.price()
        );

        return ResponseEntity.ok(confirmed);
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }
}
