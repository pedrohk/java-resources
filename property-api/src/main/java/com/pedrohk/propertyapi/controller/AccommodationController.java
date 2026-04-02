package com.pedrohk.propertyapi.controller;

import com.pedrohk.propertyapi.model.Accommodation;
import org.springframework.web.bind.annotation.*;
import com.pedrohk.propertyapi.repository.AccommodationRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/properties")
public class AccommodationController {

    private final AccommodationRepository repository;

    public AccommodationController(AccommodationRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Accommodation create(@RequestBody Accommodation acc) {
        var toSave = new Accommodation(
                acc.id() == null ? UUID.randomUUID() : acc.id(),
                acc.hotelName(),
                acc.roomNumber(),
                acc.pricePerNight(),
                acc.checkIn(),
                acc.checkOut()
        );
        return repository.save(toSave);
    }

    @GetMapping
    public List<Accommodation> readAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Accommodation readOne(@PathVariable UUID id) {
        return repository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        repository.deleteById(id);
    }
}

