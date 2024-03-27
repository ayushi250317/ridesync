package com.app.ridesync.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ridesync.entities.Location;
import com.app.ridesync.repositories.LocationRepository;

/**
 * Service class for managing locations.
 * Provides methods for adding, updating, and finding locations.
 */
@Service
public class LocationService {

	@Autowired
	private LocationRepository locationRepository;

	/**
	 * Adds a new location to the repository.
	 */
	public Location addLocation(Location location) {
		return locationRepository.save(location);
	}

	/**
	 * Updates an existing location in the repository.
	 */
	public Location updateLocation(Location location) {
		Location oldLoc = locationRepository.findByLocationId(location.getLocationId());

		oldLoc.setAddress(location.getAddress());
		oldLoc.setLandmark(location.getLandmark());
		oldLoc.setLattitude(location.getLattitude());
		oldLoc.setLongitude(location.getLongitude());

		locationRepository.save(oldLoc);

		return oldLoc;
	}

	/**
	 * Finds a location by its ID.
	 */
	public Location findLocationById(Integer Id) {
		return locationRepository.findByLocationId(Id);
	}

}
