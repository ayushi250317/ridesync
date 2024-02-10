package com.app.ridesync.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ridesync.dto.requests.RegisterRequest;
import com.app.ridesync.entities.Ride;
import com.app.ridesync.inputs.RideInput;
import com.app.ridesync.services.AuthenticationService;
import com.app.ridesync.services.RideInfoService;
import com.app.ridesync.services.RideService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/ride")
@RestController
@RequiredArgsConstructor
public class RideController {
	
	@Autowired
	private RideService rideService;
	
	@PostMapping("/addRide")
	public String addRide(@RequestBody RideInput input) { // this format contains, ride details, ride info details, location details, vehicle details as well.
		rideService.addRide(input); // add(Ride details)
		return null;
	}

}
