package com.app.ridesync.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ridesync.entities.Location;
import com.app.ridesync.repositories.LocationRepository;

@Service
public class LocationService {
	
	@Autowired
	private LocationRepository locationRepository;
	
	public long addLocation(Location location) {
		Location result = locationRepository.save(location);
		return result.getLocationId();
	}
	
	public Optional<Location> getLocationById(int Id) {
		return locationRepository.findByLocationId(Id);
	}

}
