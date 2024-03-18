package com.app.ridesync.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.app.ridesync.dto.requests.RideSearchRequest;
import com.app.ridesync.dto.responses.ApiResponse;
import com.app.ridesync.projections.SearchResultProjection;
import com.app.ridesync.services.JwtService;
import com.app.ridesync.services.RideSearchService;

@RequestMapping(path = "api/v1/geo")
@CrossOrigin(origins = "*")

@Controller
public class RideSearchController {
	private final RideSearchService searchService;
    private final JwtService jwtService;
    
	@Autowired
	public RideSearchController(RideSearchService searchService, JwtService jwtService) {
		this.searchService = searchService;
		this.jwtService = jwtService;
	}

	@PostMapping("/search")
	public  ResponseEntity<ApiResponse<List<SearchResultProjection>>> GetRides(@RequestHeader("Authorization") String jwtToken, @RequestBody RideSearchRequest request) {
		try {
			Integer userId = jwtService.extractUserId(jwtToken.substring(7));
			List<SearchResultProjection> rides = searchService.findRides(userId, request.source(),request.destination(), request.rideTime());
			return ResponseEntity.status(HttpStatus.OK)
								 .body(new ApiResponse<>(rides, true, "Result set was retrieved successfully"));

		}catch (Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
								 .body(new ApiResponse<>(null, false, "Result set retrieval failed with the following error: " + e.getMessage()));	
		}
	}

}
