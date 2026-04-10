package com.pedrohk.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HotelConfigService {

    @Value("${hotel.name}")
    private String hotelName;

    @Value("${hotel.base-price}")
    private double basePrice;

    @Value("${hotel.discount-rate:0.05}")
    private double discountRate;

    @Value("#{${hotel.base-price} * (1 + ${hotel.tax-rate})}")
    private double finalPriceWithTax;

    @Value("${hotel.available-floors}")
    private List<Integer> availableFloors;

    @Value("#{systemProperties['user.home']}")
    private String userHomeFolder;

    public String getHotelName() { return hotelName; }
    public double getBasePrice() { return basePrice; }
    public double getDiscountRate() { return discountRate; }
    public double getFinalPriceWithTax() { return finalPriceWithTax; }
    public List<Integer> getAvailableFloors() { return availableFloors; }
    public String getUserHomeFolder() { return userHomeFolder; }
}
