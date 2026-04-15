package com.pedrohk.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String number;
    private String type;
    private boolean occupied;

    public Room() {}

    public Room(String number, String type, boolean occupied) {
        this.number = number;
        this.type = type;
        this.occupied = occupied;
    }

    public Long getId() { return id; }
    public String getNumber() { return number; }
    public String getType() { return type; }
    public boolean isOccupied() { return occupied; }
    public void setOccupied(boolean occupied) { this.occupied = occupied; }
}
