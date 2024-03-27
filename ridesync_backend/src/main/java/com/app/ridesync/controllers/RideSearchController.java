package com.app.ridesync.controllers;

import java.time.LocalDateTime;
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
import com.google.maps.model.LatLng;

/**
 * Controller class handling ride search-related endpoints.
 */
@RequestMapping(path = "/api/v1/geo")
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

	/**
	 * Endpoint for searching rides based on criteria.
	 */
	@PostMapping("/search")
	public ResponseEntity<ApiResponse<List<SearchResultProjection>>> GetRides(
			@RequestHeader("Authorization") String jwtToken, @RequestBody RideSearchRequest request) {
		try {
			Integer userId = jwtService.extractUserId(jwtToken.substring(7));
			LatLng source = request.source();
			LatLng destination = request.destination();
			LocalDateTime rideTime = request.rideTime();
			List<SearchResultProjection> rides = searchService.findRides(userId, source,
					destination, rideTime);
			ApiResponse<List<SearchResultProjection>> response = new ApiResponse<>(rides, true,
					"Result set was retrieved successfully");
			return ResponseEntity.status(HttpStatus.OK)
					.body(response);

		} catch (Exception e) {
			ApiResponse<List<SearchResultProjection>> response = new ApiResponse<>(null, false,
					"Result set retrieval failed with the following error: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(response);
		}
	}

}
