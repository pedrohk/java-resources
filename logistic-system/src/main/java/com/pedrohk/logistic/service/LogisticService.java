package com.pedrohk.logistic.service;

import com.pedrohk.logistic.model.Dimensions;
import com.pedrohk.logistic.model.TransportType;
import com.pedrohk.logistic.strategy.DynamicPricingRegistry;

public class LogisticService {
    private final DynamicPricingRegistry registry;

    public LogisticService(DynamicPricingRegistry registry) {
        this.registry = registry;
    }

    public double calculateFreight(TransportType type, Dimensions dim, double weight) {
        return registry.getPrice(type, dim, weight);
    }
}
