package com.pedrohk.repository;

import com.pedrohk.model.Booking;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends ListCrudRepository<Booking, Long> {
}
