package com.app.ridesync.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.ridesync.entities.Ride;

public interface RideRepository extends JpaRepository<Ride, Integer> {
	Optional<Ride> findByRideId(Integer rideId);
    
    Optional<Ride> findByUserId(Integer userId);
}
