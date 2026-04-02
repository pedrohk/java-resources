package com.pedrohk.model;

public class HotelBooking {
    private String guestName;
    private int days;
    private double pricePerDay;

    public HotelBooking(String guestName, int days, double pricePerDay) {
        this.guestName = guestName;
        this.days = days;
        this.pricePerDay = pricePerDay;
    }

    public double calculateTotal() {
        return days * pricePerDay;
    }

    public String getGuestName() { return guestName; }
}
