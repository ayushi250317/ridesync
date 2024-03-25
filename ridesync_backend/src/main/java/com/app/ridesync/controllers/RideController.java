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
import com.app.ridesync.dto.responses.GetRidesResponse;
import com.app.ridesync.dto.responses.RideResponse;
import com.app.ridesync.projections.RideDetailProjection;
import com.app.ridesync.projections.RideHistoryProjection;
import com.app.ridesync.services.JwtService;
import com.app.ridesync.services.RideService;

import lombok.RequiredArgsConstructor;


@CrossOrigin(origins="*")
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


	@PostMapping("/addRide")
	public RideResponse addRide(@RequestHeader("Authorization") String jwtToken, @RequestBody RideInput input) {

		Integer userId = jwtService.extractUserId(jwtToken.substring(7));
		input.setUserId(userId);
		return rideService.addRide(input);
	}

	//only updates start time, description, seats available,vehicle Id
	@PostMapping("/updateRide")
	public ResponseEntity<ApiResponse<RideResponse>> updateRide(@RequestHeader("Authorization") String jwtToken, @RequestBody RideInput input) {
		try {
			Integer userId = jwtService.extractUserId(jwtToken.substring(7));
			input.setUserId(userId);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>(null, false, "ERROR: "+e.getMessage()));
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse<>(rideService.updateRide(input), true, "Result set was retrieved successfully"));
	}

	@GetMapping("/getRides/{userId}")
	public ResponseEntity<ApiResponse<List<RideHistoryProjection>>> getRidesForUser(@PathVariable Integer userId) {
		try {
			List<RideHistoryProjection> rideHistory = rideService.getRideHistoryProjectionByUserId(userId);
			return ResponseEntity.status(HttpStatus.OK)
								 .body(new ApiResponse<>(rideHistory, true, "Result set was retrieved successfully"));

		}catch (Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
								 .body(new ApiResponse<>(null, false, "Result set retrieval failed with the following error " + e.getMessage()));	
		}
	}


	@PutMapping("/updatePickupLocation")
	public ResponseEntity<ApiResponse<RideInfoResponse>> updatePickupLocation(@RequestHeader("Authorization") String jwtToken, @RequestBody PickupLocationRequest input) {
		try {
			Integer userId = jwtService.extractUserId(jwtToken.substring(7));
			RideInfoResponse res = rideInfoService.updatePickupLocation(input.getRideId(), userId, input.getLocation());

			return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse<>(res, true, "Update successful"));
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>(null, false, "Update Failed!"));
		}
		}

	@GetMapping("/getAllTripMembers/{rideId}")
	public ResponseEntity<ApiResponse<List<RideInfoResponse>>> getAllTripDetails(@PathVariable Integer rideId){
	try {
			List<RideInfoResponse> res = rideInfoService.getAllMembers(rideId);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse<>(res, true, "Fetched successfully"));
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>(null, false, "Fetch Failed with the following error:"+e.getMessage()));
		}
    }

	
	@GetMapping("/getRideDetail/{rideId}")
	public ResponseEntity<ApiResponse<RideDetailProjection>> getRideDetail(@PathVariable Integer rideId) {
		try {
			RideDetailProjection rideHistory = rideService.getRideDetailProjection(rideId);
			return ResponseEntity.status(HttpStatus.OK)
								 .body(new ApiResponse<>(rideHistory, true, "Result set was retrieved successfully"));

		}catch (Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
								 .body(new ApiResponse<>(null, false, "Result set retrieval failed with the following error " + e.getMessage()));	
		}
	}

	@PutMapping("/updateRideStatus")
	public ResponseEntity<ApiResponse<Object>> updateRideStatus(@RequestHeader("Authorization") String jwtToken, @RequestBody RideStatusUpdateRequest input){
		try {
			Integer userId = jwtService.extractUserId(jwtToken.substring(7));
			rideService.updateStatus(input.getRideId(), userId, input.getRideStatus());
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse<>(true, true, "Update Successful"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>(null, false, "Update failed with the following error: " + e.getMessage()));
		}
	}

	@GetMapping("/getDriverLocation/{rideId}")
	public ResponseEntity<ApiResponse<RideInfoResponse>> getRiderLocation(@PathVariable Integer rideId) {
		try {
			RideInfoResponse rideInfoResponse=rideInfoService.getDriverLocation(rideId);
			return ResponseEntity.status(HttpStatus.OK)
								 .body(new ApiResponse<>(rideInfoResponse, true, "Result set was retrieved successfully"));

		}catch (Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
								 .body(new ApiResponse<>(null, false, "Result set retrieval failed with the following error " + e.getMessage()));	
		}
	}

}

