package com.pedrohk.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CurrencyService {

    private final RestTemplate restTemplate;

    public CurrencyService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public double convertToUsd(double brlAmount) {
        try {
            Map<String, Double> response = restTemplate.getForObject(
                    "https://exchangerate-api.com", Map.class);

            if (response != null && response.containsKey("rates")) {
                Map<String, Double> rates = (Map<String, Double>) (Object) response.get("rates");
                return brlAmount * rates.get("USD");
            }
        } catch (Exception e) {
            return brlAmount / 5.0;
        }
        return brlAmount / 5.0;
    }
}
