package com.pedrohk.logistic.strategy.impl;

import com.pedrohk.logistic.model.Dimensions;
import com.pedrohk.logistic.strategy.FreightStrategy;

public class TruckStrategy implements FreightStrategy {
    private final double ratePerVolume;
    public TruckStrategy(double ratePerVolume) { this.ratePerVolume = ratePerVolume; }

    @Override
    public double calculate(Dimensions dimensions, double weight) {
        return (dimensions.volume() * ratePerVolume) + (weight * 0.5);
    }
}
