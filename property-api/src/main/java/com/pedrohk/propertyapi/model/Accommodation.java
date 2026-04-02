package com.pedrohk.propertyapi.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import java.time.LocalDate;
import java.util.UUID;

@Table("accommodations")
public record Accommodation(
        @PrimaryKey UUID id,
        String hotelName,
        String roomNumber,
        double pricePerNight,
        LocalDate checkIn,
        LocalDate checkOut
) {}

