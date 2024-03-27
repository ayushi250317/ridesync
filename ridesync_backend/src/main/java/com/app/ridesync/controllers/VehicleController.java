package com.app.ridesync.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ridesync.dto.requests.VehicleInput;
import com.app.ridesync.dto.responses.GetVehicleResponse;
import com.app.ridesync.dto.responses.VehicleResponse;
import com.app.ridesync.services.JwtService;
import com.app.ridesync.services.VehicleService;

import lombok.RequiredArgsConstructor;

/**
 * Controller class handling vehicle-related endpoints.
 */
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/vehicle")
@RestController
@RequiredArgsConstructor
public class VehicleController {

	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private JwtService jwtService;

	/**
	 * Endpoint for adding a new vehicle.
	 */
	@PostMapping("/addVehicle")
	public VehicleResponse addVehicle(@RequestHeader("Authorization") String jwtToken,
			@RequestBody VehicleInput input) {
		Integer userId = jwtService.extractUserId(jwtToken.substring(7));
		input.setUserId(userId);
		VehicleResponse res = vehicleService.addVehicle(input); // add(Ride details)
		return res;
	}

	/**
	 * Endpoint for retrieving vehicles by user ID.
	 */
	@GetMapping("/getVehiclesByUserId/{id}")
	public GetVehicleResponse getVehiclesById(@PathVariable String id,
			@RequestHeader("Authorization") String jwtToken) {
		Integer userId = jwtService.extractUserId(jwtToken.substring(7));
		return vehicleService.getVehiclesByUserId(userId);
	}

	/**
	 * Endpoint for updating a vehicle.
	 */
	@PostMapping("/updateVehicle")
	public VehicleResponse updateVehicle(@RequestBody VehicleInput input) {
		return vehicleService.updateVehicleById(input);
	}

	/**
	 * Endpoint for deleting a vehicle by ID.
	 */
	@DeleteMapping("/deleteVehicle/{vehicleId}")
	public VehicleResponse deleteVehicle(@PathVariable Integer vehicleId) {
		return vehicleService.deleteVehicle(vehicleId);
	}
}
