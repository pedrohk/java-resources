package com.pedrohk.service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HotelManagerService {

    private final AtomicInteger guestCounter = new AtomicInteger(0);
    private final Lock reservationLock = new ReentrantLock();
    private boolean isRoom101Reserved = false;

    public void incrementGuestCount() {
        guestCounter.incrementAndGet();
    }

    public int getGuestCount() {
        return guestCounter.get();
    }

    public boolean reserveRoom101() {
        if (reservationLock.tryLock()) {
            try {
                if (!isRoom101Reserved) {
                    Thread.sleep(50);
                    isRoom101Reserved = true;
                    return true;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                reservationLock.unlock();
            }
        }
        return false;
    }
}
