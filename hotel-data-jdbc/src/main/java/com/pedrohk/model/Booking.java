package com.pedrohk.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import java.util.Set;

@Table("BOOKING")
public record Booking(
        @Id Long id,
        String guestName,
        String roomNumber,
        @MappedCollection(idColumn = "BOOKING") Set<ServiceItem> items
) {}

