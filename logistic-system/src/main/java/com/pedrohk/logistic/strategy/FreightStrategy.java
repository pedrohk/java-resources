package com.pedrohk.logistic.strategy;

import com.pedrohk.logistic.model.Dimensions;

public interface FreightStrategy {
    double calculate(Dimensions dimensions, double weight);
}
