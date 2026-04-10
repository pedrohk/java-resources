package com.pedrohk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FrontDeskService {

    private final InventoryService inventoryService;
    private MarketingService marketingService;

    public FrontDeskService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Autowired
    public void setMarketingService(MarketingService marketingService) {
        this.marketingService = marketingService;
    }

    public String registerGuest(int room) {
        if (inventoryService.checkAvailability(room)) {
            String msg = "Guest registered in room " + room;
            if (marketingService != null) {
                msg += " - Message: " + marketingService.getNewsletter();
            }
            return msg;
        }
        return "No rooms available";
    }

    public InventoryService getInventoryService() {
        return inventoryService;
    }

    public MarketingService getMarketingService() {
        return marketingService;
    }
}
