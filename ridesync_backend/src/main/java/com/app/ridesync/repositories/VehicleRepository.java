package com.app.ridesync.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.ridesync.entities.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    
    List<Vehicle> findByUserId(Integer userId);
    
    Vehicle findByVehicleId(Integer vehicleId);
}
