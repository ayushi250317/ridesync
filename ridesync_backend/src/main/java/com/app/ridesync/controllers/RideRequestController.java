package com.app.ridesync.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @CrossOrigin(origins="*")
    @PutMapping("/updateRideRequest/{id}")
    public ResponseEntity<RideRequestResponse> updateRideRequest(@RequestHeader("Authorization") String jwtToken,@PathVariable Integer id, @RequestBody RideRequest request){
        return ResponseEntity.ok(rideRequestService.updateRide(jwtToken,id,request));
    }
}
