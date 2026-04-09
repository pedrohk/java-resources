package com.pedrohk.service;

import com.pedrohk.repository.RoomRepository;
import org.springframework.stereotype.Service;

@Service
public class CheckInService {
    private final RoomRepository roomRepository;
    private final NotificationService notificationService;

    public CheckInService(RoomRepository roomRepository, NotificationService notificationService) {
        this.roomRepository = roomRepository;
        this.notificationService = notificationService;
    }

    public boolean performCheckIn(int roomNumber, String guestName) {
        if (roomRepository.isAvailable(roomNumber)) {
            roomRepository.updateStatus(roomNumber, false);
            notificationService.send("Guest " + guestName + " checked into room " + roomNumber);
            return true;
        }
        return false;
    }
}
