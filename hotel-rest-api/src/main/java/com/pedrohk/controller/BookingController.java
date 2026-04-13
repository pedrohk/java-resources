package com.pedrohk.controller;

import com.pedrohk.model.Booking;
import com.pedrohk.service.CurrencyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.UUID;

@RestController
@RequestMapping("/api/hotel/bookings")
public class BookingController {

    private final CurrencyService currencyService;

    public BookingController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping
    public Booking createBooking(@RequestBody Booking request) {
        double usdPrice = currencyService.convertToUsd(request.priceInBRL());
        return new Booking(
                UUID.randomUUID().toString(),
                request.guestName(),
                request.priceInBRL(),
                usdPrice
        );
    }
}
