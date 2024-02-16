package com.app.ridesync.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ridesync.inputs.VehicleInput;
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
	public String addRide(@RequestHeader("Authentication") String jwtToken, @RequestBody VehicleInput input) {
		
		String userId = jwtService.extractUserEmail(jwtToken);
		input.setUserId(userId);
		vehicleService.addVehicle(input); // add(Ride details)
		return "Successfully added the user";
	}

}
