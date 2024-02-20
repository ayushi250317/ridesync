package com.app.ridesync.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ridesync.entities.GeoPoint;
import com.app.ridesync.repositories.GeoPointRepository;

@Service
public class GeoPointService {
	private final GeoPointRepository geoPointRepository;
	
	@Autowired
	public GeoPointService(GeoPointRepository geoPointRepository) {
		this.geoPointRepository = geoPointRepository;
	}
	
	public GeoPoint saveGeoPoints(GeoPoint geoPoints) {
			return geoPointRepository.save(geoPoints);			
	}
}
