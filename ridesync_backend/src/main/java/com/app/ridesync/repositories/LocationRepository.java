package com.app.ridesync.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.app.ridesync.entities.Location;


public interface LocationRepository extends JpaRepository<Location, Integer> {
    Optional<Location> findByLocationId(Integer locationId);
}
