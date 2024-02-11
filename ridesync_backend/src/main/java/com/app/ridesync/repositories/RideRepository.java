package com.app.ridesync.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.ridesync.entities.Ride;

@Repository
public interface RideRepository extends JpaRepository<Ride, Integer> {
	Optional<Ride> findByRideId(Integer rideId);
}
