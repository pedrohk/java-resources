package com.pedrohk.propertyapi.repository;

import com.pedrohk.propertyapi.model.Accommodation;
import org.springframework.data.cassandra.repository.CassandraRepository;
import java.util.UUID;

public interface AccommodationRepository extends CassandraRepository<Accommodation, UUID> {
}

