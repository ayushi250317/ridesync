package com.app.ridesync.services;


import org.springframework.beans.factory.annotation.Autowired;

import com.app.ridesync.entities.Vehicle;
import com.app.ridesync.repositories.VehicleRepository;

public class VehicleService {
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	public int addVehicle(Vehicle vehicle) {
		
		Vehicle response = vehicleRepository.save(vehicle); //add the insurance number ID.
		return response.getVehicleId();
	}
}
