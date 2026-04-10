package com.pedrohk.service;

import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    public boolean checkAvailability(int room) {
        return room > 0 && room < 500;
    }
}
