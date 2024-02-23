package com.app.ridesync.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.app.ridesync.dto.requests.RideInput;
import com.app.ridesync.dto.responses.GetRidesResponse;
import com.app.ridesync.dto.responses.RideResponse;
import com.app.ridesync.services.JwtService;
import com.app.ridesync.services.RideService;

import lombok.RequiredArgsConstructor;


@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/ride")
@RestController
@RequiredArgsConstructor
public class RideController {
	
	@Autowired
	private RideService rideService;
	@Autowired
	private JwtService jwtService;
	
	
	@PostMapping("/addRide")
	public RideResponse addRide(@RequestHeader("Authorization") String jwtToken, @RequestBody RideInput input) {	
		Integer userId = jwtService.extractUserId(jwtToken.substring(7));
		input.setUserId(userId);
		return rideService.addRide(input);
	}
	
	//only updates start time, description, seats available,vehicle Id
	@PostMapping("/updateRide")
	public RideResponse updateRide(@RequestHeader("Authorization") String jwtToken, @RequestBody RideInput input) {
		Integer userId = jwtService.extractUserId(jwtToken.substring(7));
		input.setUserId(userId);	
		return rideService.updateRide(input);
	}
	
	@GetMapping("/getRide/{userId}")
	public GetRidesResponse getRide(@PathVariable Integer userId){	
		return rideService.getRides(userId);
	}
}
