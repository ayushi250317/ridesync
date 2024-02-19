package com.app.ridesync.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.ridesync.entities.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
    Location findByLocationId(Integer locationId);

}
