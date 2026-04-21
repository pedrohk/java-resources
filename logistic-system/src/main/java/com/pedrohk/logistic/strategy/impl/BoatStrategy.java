package com.pedrohk.logistic.strategy.impl;

import com.pedrohk.logistic.model.Dimensions;
import com.pedrohk.logistic.strategy.FreightStrategy;

public class BoatStrategy implements FreightStrategy {
    @Override
    public double calculate(Dimensions dimensions, double weight) {
        return dimensions.volume() * 0.2 + weight * 0.1;
    }
}
