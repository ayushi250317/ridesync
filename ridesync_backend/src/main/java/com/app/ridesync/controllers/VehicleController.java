package com.app.ridesync.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ridesync.dto.requests.VehicleInput;
import com.app.ridesync.dto.responses.VehicleResponse;
import com.app.ridesync.dto.responses.getVehicleResponse;
import com.app.ridesync.services.JwtService;
import com.app.ridesync.services.VehicleService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/vehicle")
@RestController
@RequiredArgsConstructor
public class VehicleController {
	
	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private JwtService jwtService;
	
	@PostMapping("/addVehicle")
	public VehicleResponse addRide(@RequestHeader("Authentication") String jwtToken, @RequestBody VehicleInput input) {
		
		String userId = jwtService.extractUserEmail(jwtToken);
		input.setUserId(userId);
		VehicleResponse res =vehicleService.addVehicle(input); // add(Ride details)
		return res;
	}
	
	@GetMapping("/getVehiclesByUserId/{id}")
	public getVehicleResponse getDocumentsById(@PathVariable String id, @RequestHeader("Authentication") String jwtToken){
		String userId = jwtService.extractUserEmail(jwtToken);
		return vehicleService.getVehiclesByUserId(userId);
	}
}
