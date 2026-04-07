package com.pedrohk.service;

import com.pedrohk.annotation.PremiumOnly;

public class BookingService {

    @PremiumOnly(message = "Attention: Only VIP guests can book the Presidential Suite!")
    public String bookPresidentialSuite() {
        return "Presidential Suite booked";
    }

    public String bookStandardRoom() {
        return "Standard room booked";
    }
}
