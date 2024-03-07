package com.app.ridesync.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.ridesync.dto.requests.RideRequest;
import com.app.ridesync.dto.responses.RideRequestResponse;
import com.app.ridesync.services.RideRequestService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins="*")
@RequestMapping("/api/v1/request")
@RestController
@RequiredArgsConstructor
public class RideRequestController {

    private final RideRequestService rideRequestService;

    @PostMapping("/addRequest")
	public ResponseEntity<RideRequestResponse> addRide(@RequestHeader("Authorization") String jwtToken, @RequestBody RideRequest input) {
        return ResponseEntity.ok(rideRequestService.requestRide(jwtToken,input));
	}

    @GetMapping("/getRideRequest")
    public ResponseEntity<RideRequestResponse> getRide(@RequestParam Integer rideId ){
        return ResponseEntity.ok(rideRequestService.getRides(rideId));
    }

    @PatchMapping("/updateRideRequest/{id}")
    public ResponseEntity<RideRequestResponse> updateRideRequest(@PathVariable Integer id, @RequestBody RideRequest request){
        return ResponseEntity.ok(rideRequestService.updateRide(id,request));
    }
}
