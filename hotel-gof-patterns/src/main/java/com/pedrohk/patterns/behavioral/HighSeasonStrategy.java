package com.pedrohk.patterns.behavioral;

public class HighSeasonStrategy implements PricingStrategy {
    @Override
    public double applyPrice(double basePrice) {
        return basePrice * 1.8;
    }
}
