package com.app.ridesync.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ridesync.dto.requests.VehicleInput;
import com.app.ridesync.dto.responses.VehicleResponse;
import com.app.ridesync.dto.responses.GetVehicleResponse;
import com.app.ridesync.entities.Vehicle;
import com.app.ridesync.repositories.VehicleRepository;

@Service
public class VehicleService {
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	public VehicleResponse addVehicle(VehicleInput input) {
		VehicleResponse res = new VehicleResponse();
		
		try {
		Vehicle vehicle = new Vehicle();
		vehicle.setRegNo(input.getRegNo());
		vehicle.setDocumentId(input.getDocumentId());
		vehicle.setModel(input.getModel());
		vehicle.setMake(input.getMake());
		vehicle.setType(input.getType());
		vehicle.setUserId(input.getUserId());
		Vehicle response = vehicleRepository.save(vehicle); 
		
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
	
	public GetVehicleResponse getVehiclesByUserId(Integer userId) {
		GetVehicleResponse res = new GetVehicleResponse();
		try {
		
		res.setVehicles(vehicleRepository.findByUserId(userId));
		
		}catch(Exception e) {
			res.setMessage(e.toString());
			res.setSuccess(true);
			return res;
		}
		res.setMessage("Vehicles Fetched Successfully");
		res.setSuccess(true);
		return res;
	}

	public VehicleResponse updateVehicleById(VehicleInput input) {
		VehicleResponse res = new VehicleResponse();
		try {
		Vehicle vehicle = vehicleRepository.findByVehicleId(input.getVehicleId());
        
        vehicle.setMake(input.getMake());
        vehicle.setDocumentId(input.getDocumentId());
        vehicle.setModel(input.getModel());
        vehicle.setRegNo(input.getRegNo());
        vehicle.setType(input.getType());
        
        res.setVehicle(vehicleRepository.save(vehicle));
		}catch(Exception e) {
			res.setMessage(e.toString());
			res.setSuccess(false);
			return res;
		}
		res.setMessage("Updated Selected Vehicle Successfully");
		res.setSuccess(true);
        return res;
	}

	public VehicleResponse deleteVehicle(Integer vehicleId) {
		VehicleResponse res = new VehicleResponse();
		try {
		Vehicle vehicle = vehicleRepository.findByVehicleId(vehicleId);
       
        res.setVehicle(vehicle);
        vehicleRepository.delete(vehicle);
		}catch(Exception e) {
			res.setMessage(e.toString());
			res.setSuccess(false);
			return res;
		}
		res.setMessage("Deleted Selected Vehicle Successfully");
		res.setSuccess(true);
		return res;
	}
}