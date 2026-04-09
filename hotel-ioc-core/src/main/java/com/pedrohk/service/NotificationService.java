package com.pedrohk.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    public void send(String message) {
        System.out.println("Notification: " + message);
    }
}
