package com.pedrohk.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Room {
    @Id
    private Integer number;
    private String status;

    public Room() {}
    public Room(Integer number, String status) {
        this.number = number;
        this.status = status;
    }

    public Integer getNumber() { return number; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
