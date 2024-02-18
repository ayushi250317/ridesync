package com.app.ridesync.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ridesync.dto.requests.RideInput;
import com.app.ridesync.dto.responses.RideResponse;
import com.app.ridesync.services.JwtService;
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
	
	
	@PostMapping("/addRide")
	public RideResponse addRide(@RequestHeader("Authentication") String jwtToken, @RequestBody RideInput input) {
		
		Integer userId = jwtService.extractUserEmail(jwtToken);
		input.setUserId(userId);
		RideResponse res = rideService.addRide(input); // add(Ride details)
		return res;
	}
}
