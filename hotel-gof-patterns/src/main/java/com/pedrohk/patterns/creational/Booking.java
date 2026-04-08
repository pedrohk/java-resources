package com.pedrohk.patterns.creational;

public class Booking {
    private String guestName;
    private boolean hasWifi;
    private boolean hasBreakfast;

    public String getGuestName() {
        return guestName;
    }

    public static class Builder {
        private String guestName;
        private boolean hasWifi;
        private boolean hasBreakfast;

        public Builder guestName(String guestName) {
            this.guestName = guestName;
            return this;
        }

        public Builder withWifi() {
            this.hasWifi = true;
            return this;
        }

        public Builder withBreakfast() {
            this.hasBreakfast = true;
            return this;
        }

        public Booking build() {
            Booking booking = new Booking();
            booking.guestName = this.guestName;
            booking.hasWifi = this.hasWifi;
            booking.hasBreakfast = this.hasBreakfast;
            return booking;
        }
    }
}
