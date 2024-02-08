package com.app.ridesync.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.app.ridesync.entities.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
	Optional<Vehicle> findByVehicleId(Integer documentId);
    
    Optional<Vehicle> findByUserId(Integer userId);
}
