package com.app.ridesync.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ridesync.entities.Location;
import com.app.ridesync.repositories.LocationRepository;

@Service
public class LocationService {
	
	@Autowired
	private LocationRepository locationRepository;
	
	public Location addLocation(Location location) { 
		return locationRepository.save(location);
	}
	
	public Location updateLocation(Location location) {
		Location oldLoc = locationRepository.findByLocationId(location.getLocationId());
		
		oldLoc.setAddress(location.getAddress());
		oldLoc.setLandmark(location.getLandmark());
		oldLoc.setLattitude(location.getLattitude());
		oldLoc.setLongitude(location.getLongitude());
		
		locationRepository.save(oldLoc);
		
		return oldLoc;
	}
	
	public Location findLocationById(Integer Id) {
		return locationRepository.findByLocationId(Id);
	}

}
