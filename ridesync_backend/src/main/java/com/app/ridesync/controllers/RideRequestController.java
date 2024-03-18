package com.app.ridesync.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.ridesync.dto.requests.RideRequest;
import com.app.ridesync.dto.responses.ApiResponse;
import com.app.ridesync.dto.responses.RideRequestResponse;
import com.app.ridesync.projections.RideRequestProjection;
import com.app.ridesync.services.RideRequestService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/request")
@RestController
@RequiredArgsConstructor
public class RideRequestController {

    private final RideRequestService rideRequestService;

    @PostMapping("/addRequest")
    public ResponseEntity<RideRequestResponse> addRide(@RequestHeader("Authorization") String jwtToken,
            @RequestBody RideRequest input) {
        try {
            return ResponseEntity.ok(rideRequestService.requestRide(jwtToken, input));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(RideRequestResponse.builder().success(false).message(e.getMessage()).build());
        }
    }

    @GetMapping("/getRideRequest")
    public ResponseEntity<ApiResponse<List<RideRequestProjection>>> getRide(@RequestParam Integer rideId) {
      try {
            List<RideRequestProjection> rides = rideRequestService.getRides(rideId);
            return ResponseEntity.status(HttpStatus.OK)
								 .body(new ApiResponse<>(rides, true, "Result set was retrieved successfully"));
        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
							 .body(new ApiResponse<>(null, false, "Result set retrieval failed with the following error: " + e.getMessage())); 
        }
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/updateRideRequest/{id}")
    public ResponseEntity<RideRequestResponse> updateRideRequest(@RequestHeader("Authorization") String jwtToken,
            @PathVariable Integer id, @RequestBody RideRequest request) {
        try {
            return ResponseEntity.ok(rideRequestService.updateRide(jwtToken, id, request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(RideRequestResponse.builder().success(false).message(e.getMessage()).build());
        }
    }
}
