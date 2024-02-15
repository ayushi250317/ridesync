package com.app.ridesync.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<String> create(@RequestBody GeoPoint geoPoints) {
		return geoPointService.saveGeoPoints(geoPoints);			
	}
}
