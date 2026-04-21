package com.pedrohk.logistic.strategy;

import com.pedrohk.logistic.model.TransportType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DynamicPricingRegistry {
    private final Map<TransportType, FreightStrategy> strategies = new ConcurrentHashMap<>();

    public void updateStrategy(TransportType type, FreightStrategy strategy) {
        strategies.put(type, strategy);
    }

    public double getPrice(TransportType type, com.pedrohk.logistic.model.Dimensions dim, double weight) {
        FreightStrategy strategy = strategies.get(type);
        if (strategy == null) throw new IllegalArgumentException("No strategy for: " + type);
        return strategy.calculate(dim, weight);
    }
}
