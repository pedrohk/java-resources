package com.pedrohk.service;

import com.pedrohk.model.Room;
import com.pedrohk.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CheckOutService {

    private final RoomRepository repository;

    public CheckOutService(RoomRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void performCheckOut(Integer roomNumber, boolean simulateFailure) {
        Room room = repository.findById(roomNumber)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        room.setStatus("AVAILABLE");
        repository.save(room);

        if (simulateFailure) {
            throw new RuntimeException("Payment system failed! Triggering rollback...");
        }
    }
}
