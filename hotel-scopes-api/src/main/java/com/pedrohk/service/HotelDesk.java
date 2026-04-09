package com.pedrohk.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class HotelDesk {
    private final AtomicInteger requestCounter = new AtomicInteger(0);

    public int incrementAndGetRequests() {
        return requestCounter.incrementAndGet();
    }
}
