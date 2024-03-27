package com.app.ridesync.controllers;

import java.util.List;

import com.app.ridesync.dto.requests.PickupLocationRequest;
import com.app.ridesync.dto.requests.RideStatusUpdateRequest;
import com.app.ridesync.dto.responses.RideInfoResponse;
import com.app.ridesync.services.RideInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.ridesync.dto.requests.RideInput;
import com.app.ridesync.dto.responses.ApiResponse;
import com.app.ridesync.dto.responses.RideResponse;
import com.app.ridesync.projections.RideDetailProjection;
import com.app.ridesync.projections.RideHistoryProjection;
import com.app.ridesync.services.JwtService;
import com.app.ridesync.services.RideService;

import lombok.RequiredArgsConstructor;

/**
 * Controller class handling ride-related endpoints.
 */
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/ride")
@RestController
@RequiredArgsConstructor
public class RideController {

	@Autowired
	private RideService rideService;
	@Autowired
	private RideInfoService rideInfoService;
	@Autowired
	private JwtService jwtService;

	/**
	 * Endpoint for adding a new ride.
	 */
	@PostMapping("/addRide")
	public RideResponse addRide(@RequestHeader("Authorization") String jwtToken, @RequestBody RideInput input) {

		Integer userId = jwtService.extractUserId(jwtToken.substring(7));
		input.setUserId(userId);
		return rideService.addRide(input);
	}

	/**
	 * Endpoint for updating ride details.
	 * only updates start time, description, seats available,vehicle Id
	 */
	@PostMapping("/updateRide")
	public ResponseEntity<ApiResponse<RideResponse>> updateRide(@RequestHeader("Authorization") String jwtToken,
			@RequestBody RideInput input) {
		try {
			Integer userId = jwtService.extractUserId(jwtToken.substring(7));
			input.setUserId(userId);
			ApiResponse<RideResponse> response = new ApiResponse<>(rideService.updateRide(input), true,
					"Result set was retrieved successfully");
			return ResponseEntity.status(HttpStatus.OK)
					.body(response);
		} catch (Exception e) {
			ApiResponse<RideResponse> response = new ApiResponse<>(null, false, "ERROR: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(response);
		}
	}

	/**
	 * Endpoint for retrieving ride history for a user.
	 */
	@GetMapping("/getRides/{userId}")
	public ResponseEntity<ApiResponse<List<RideHistoryProjection>>> getRidesForUser(@PathVariable Integer userId) {
		try {
			List<RideHistoryProjection> rideHistory = rideService.getRideHistoryProjectionByUserId(userId);
			ApiResponse<List<RideHistoryProjection>> response = new ApiResponse<>(rideHistory, true,
					"Result set was retrieved successfully");
			return ResponseEntity.status(HttpStatus.OK)
					.body(response);

		} catch (Exception e) {
			ApiResponse<List<RideHistoryProjection>> response = new ApiResponse<>(null, false,
					"Result set retrieval failed with the following error " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(response);
		}
	}

	/**
	 * Endpoint for updating pickup location of a ride.
	 */
	@PutMapping("/updatePickupLocation")
	public ResponseEntity<ApiResponse<RideInfoResponse>> updatePickupLocation(
			@RequestHeader("Authorization") String jwtToken, @RequestBody PickupLocationRequest input) {
		try {
			Integer userId = jwtService.extractUserId(jwtToken.substring(7));
			RideInfoResponse res = rideInfoService.updatePickupLocation(input.getRideId(), userId, input.getLocation());
			ApiResponse<RideInfoResponse> response = new ApiResponse<>(res, true, "Update successful");
			return ResponseEntity.status(HttpStatus.OK)
					.body(response);
		} catch (Exception e) {
			ApiResponse<RideInfoResponse> response = new ApiResponse<>(null, false, "Update Failed!");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(response);
		}
	}

	/**
	 * Endpoint for retrieving all trip members for a ride.
	 */
	@GetMapping("/getAllTripMembers/{rideId}")
	public ResponseEntity<ApiResponse<List<RideInfoResponse>>> getAllTripDetails(@PathVariable Integer rideId) {
		try {
			List<RideInfoResponse> res = rideInfoService.getAllMembers(rideId);
			ApiResponse<List<RideInfoResponse>> response = new ApiResponse<>(res, true, "Fetched successfully");
			return ResponseEntity.status(HttpStatus.OK)
					.body(response);
		} catch (Exception e) {
			ApiResponse<List<RideInfoResponse>> response = new ApiResponse<>(null, false,
					"Fetch Failed with the following error:" + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(response);
		}
	}

	/**
	 * Endpoint for retrieving detailed information about a ride.
	 */
	@GetMapping("/getRideDetail/{rideId}")
	public ResponseEntity<ApiResponse<RideDetailProjection>> getRideDetail(@PathVariable Integer rideId) {
		try {
			RideDetailProjection rideHistory = rideService.getRideDetailProjection(rideId);
			ApiResponse<RideDetailProjection> response = new ApiResponse<>(rideHistory, true,
					"Result set was retrieved successfully");
			return ResponseEntity.status(HttpStatus.OK)
					.body(response);

		} catch (Exception e) {
			ApiResponse<RideDetailProjection> response = new ApiResponse<>(null, false,
					"Result set retrieval failed with the following error " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(response);
		}
	}

	/**
	 * Endpoint for updating ride status.
	 */
	@PutMapping("/updateRideStatus")
	public ResponseEntity<ApiResponse<Object>> updateRideStatus(@RequestHeader("Authorization") String jwtToken,
			@RequestBody RideStatusUpdateRequest input) {
		try {
			Integer userId = jwtService.extractUserId(jwtToken.substring(7));
			rideService.updateStatus(input.getRideId(), userId, input.getRideStatus());
			ApiResponse<Object> response = new ApiResponse<>(true, true, "Update Successful");
			return ResponseEntity.status(HttpStatus.OK)
					.body(response);
		} catch (Exception e) {
			ApiResponse<Object> response = new ApiResponse<>(null, false,
					"Update failed with the following error: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(response);
		}
	}

	/**
	 * Endpoint for retrieving rider location for a ride.
	 */
	@GetMapping("/getDriverLocation/{rideId}")
	public ResponseEntity<ApiResponse<RideInfoResponse>> getRiderLocation(@PathVariable Integer rideId) {
		try {
			RideInfoResponse rideInfoResponse = rideInfoService.getDriverLocation(rideId);
			ApiResponse<RideInfoResponse> response = new ApiResponse<>(rideInfoResponse, true,
					"Result set was retrieved successfully");
			return ResponseEntity.status(HttpStatus.OK)
					.body(response);

		} catch (Exception e) {
			ApiResponse<RideInfoResponse> response = new ApiResponse<>(null, false,
					"Result set retrieval failed with the following error " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(response);
		}
	}

}
