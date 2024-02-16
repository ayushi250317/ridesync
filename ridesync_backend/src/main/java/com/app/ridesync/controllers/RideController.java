package com.app.ridesync.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ridesync.dto.requests.RegisterRequest;
import com.app.ridesync.dto.requests.RideInput;
import com.app.ridesync.entities.Ride;
import com.app.ridesync.services.AuthenticationService;
import com.app.ridesync.services.JwtService;
import com.app.ridesync.services.RideInfoService;
import com.app.ridesync.services.RideService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/ride")
@RestController
@RequiredArgsConstructor
public class RideController {
	
	@Autowired
	private RideService rideService;
	@Autowired
	private JwtService jwtService;
	
	// this format contains, ride details, ride info details, location details, vehicle details as well.
	@PostMapping("/addRide")
	public String addRide(@RequestHeader("Authentication") String jwtToken, @RequestBody RideInput input) {
		
		String userId = jwtService.extractUserEmail(jwtToken);
		input.setUserId(userId);
		rideService.addRide(input); // add(Ride details)
		return "Successfully added the user";
	}
}



//change lat/long to double
//change id to long
//change LocalDate to datetime or something.