package com.app.ridesync.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ridesync.dto.responses.ApiResponse;
import com.app.ridesync.entities.GeoPoint;
import com.app.ridesync.services.GeoPointService;

@RequestMapping(path = "api/v1/geo")
@RestController
public class GeoPointController {		
	private final GeoPointService geoPointService;

	@Autowired
	public GeoPointController(GeoPointService geoPointService) {
		this.geoPointService = geoPointService;
	}

	@PostMapping("/create")
	public ResponseEntity<ApiResponse<GeoPoint>> create(@RequestBody GeoPoint routeCoordinates) {
		try {
			GeoPoint createdGeoPoint = geoPointService.saveGeoPoints(routeCoordinates);
			return ResponseEntity.status(HttpStatus.OK)
								 .body(new ApiResponse<>(createdGeoPoint, true, "Entity created successfully"));

		}catch (Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
								 .body(new ApiResponse<>(null, false, "Entity creation failed with the following error " + e.getMessage()));	
		}
	}
}
