package com.app.ridesync.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.ridesync.entities.GeoPoint;
import com.app.ridesync.services.GeoPointService;

@RequestMapping(path = "api/v1/geo")
@Controller
public class GeoPointController {		
	private final GeoPointService geoPointService;
	
	@Autowired
	public GeoPointController(GeoPointService geoPointService) {
		this.geoPointService = geoPointService;
	}
	
	@PostMapping
	public void saveGeoPoints(@RequestBody GeoPoint geoPoints) {
		geoPointService.saveGeoPoints(geoPoints);
	}
}
