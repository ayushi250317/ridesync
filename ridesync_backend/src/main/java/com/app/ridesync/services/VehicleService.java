package com.app.ridesync.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ridesync.entities.Vehicle;
import com.app.ridesync.repositories.VehicleRepository;

@Service
public class VehicleService {
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	public long addVehicle(Vehicle vehicle) {
		
		Vehicle response = vehicleRepository.save(vehicle); //add the insurance number ID.
		return response.getVehicleId();
	}
}
