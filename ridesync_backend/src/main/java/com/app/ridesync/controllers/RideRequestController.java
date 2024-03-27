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

/**
 * Controller class handling ride request-related endpoints.
 */
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/request")
@RestController
@RequiredArgsConstructor
public class RideRequestController {

    private final RideRequestService rideRequestService;

    /**
     * Endpoint for adding a ride request.
     */
    @PostMapping("/addRequest")
    public ResponseEntity<RideRequestResponse> addRide(@RequestHeader("Authorization") String jwtToken,
            @RequestBody RideRequest input) {
        try {
            RideRequestResponse response = rideRequestService.requestRide(jwtToken, input);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            RideRequestResponse response = RideRequestResponse.builder().success(false).message(e.getMessage()).build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }

    /**
     * Endpoint for retrieving ride requests for a ride.
     */
    @GetMapping("/getRideRequest")
    public ResponseEntity<ApiResponse<List<RideRequestProjection>>> getRide(@RequestParam Integer rideId) {
        try {
            List<RideRequestProjection> rides = rideRequestService.getRides(rideId);
            ApiResponse<List<RideRequestProjection>> response = new ApiResponse<>(rides, true,
                    "Result set was retrieved successfully");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(response);
        } catch (Exception e) {
            ApiResponse<List<RideRequestProjection>> response = new ApiResponse<>(null, false,
                    "Result set retrieval failed with the following error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }

    /**
     * Endpoint for updating a ride request.
     */
    @CrossOrigin(origins = "*")
    @PutMapping("/updateRideRequest/{id}")
    public ResponseEntity<RideRequestResponse> updateRideRequest(@RequestHeader("Authorization") String jwtToken,
            @PathVariable Integer id, @RequestBody RideRequest request) {
        try {
            RideRequestResponse response = rideRequestService.updateRide(jwtToken, id, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            RideRequestResponse response = RideRequestResponse.builder().success(false).message(e.getMessage()).build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }
}
