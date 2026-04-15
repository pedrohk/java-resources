package com.pedrohk.controller;

import com.pedrohk.model.Room;
import com.pedrohk.repository.RoomRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomRepository repository;

    public RoomController(RoomRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Room addRoom(@RequestBody Room room) {
        return repository.save(room);
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return repository.findAll();
    }

    @PatchMapping("/{id}/occupy")
    public ResponseEntity<Room> occupyRoom(@PathVariable Long id) {
        return repository.findById(id)
                .map(room -> {
                    room.setOccupied(true);
                    return ResponseEntity.ok(repository.save(room));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
