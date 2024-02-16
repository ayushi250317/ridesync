package com.app.ridesync.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ridesync.dto.requests.VehicleInput;
import com.app.ridesync.dto.responses.VehicleResponse;
import com.app.ridesync.entities.Vehicle;
import com.app.ridesync.repositories.VehicleRepository;

@Service
public class VehicleService {
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	public VehicleResponse addVehicle(VehicleInput input) {
		VehicleResponse res = new VehicleResponse();
		
		try {
		Vehicle vehicle = new Vehicle(input.getRegNo(), input.getDocumentId(), input.getModel(), input.getMake(), input.getType(), input.getUserId());
		Vehicle response = vehicleRepository.save(vehicle); //add the insurance number ID.
		
		
		res.setVehicle(response);
		}catch(Exception e){
			res.setSuccess(false);
			res.setMessage(e.toString());
			return res;
		}
		res.setSuccess(true);
		res.setMessage("Vehicle inserted Successfully");
		return res;
	}
}
