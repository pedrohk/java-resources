package com.pedrohk.service;

import org.springframework.stereotype.Service;

@Service
public class MarketingService {
    public String getNewsletter() {
        return "Welcome to our Premium Hotel!";
    }
}
