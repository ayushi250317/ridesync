package com.app.ridesync.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ridesync.entities.Location;
import com.app.ridesync.entities.RideInfo;
import com.app.ridesync.inputs.RideInfoInput;
import com.app.ridesync.repositories.RideInfoRepository;

@Service
public class RideInfoService {
	
	@Autowired
	private RideInfoRepository rideInfoRepository;
	private LocationService locationService;
	
	public String addRideInfo(RideInfoInput riInput) { // format for data that includes location.
		
		//add location 1
		 int locationId1 = locationService.addLocation(new Location(
								 riInput.getLattitude1(),
								 riInput.getLongitude1(),
								 riInput.getLandmark1(),
								 riInput.getAddress1()
								 ));
		
		//add location 2
		 int locationId2 = locationService.addLocation(new Location(
				 riInput.getLattitude2(),
				 riInput.getLongitude2(),
				 riInput.getLandmark2(),
				 riInput.getAddress2()
				 ));
		 
		 
		//get their Ids and then save RideInfo entry.
		rideInfoRepository.save(new RideInfo(
				riInput.getRideId(),
				riInput.getUserId(),
				locationId1,
				locationId2,
				true,
				riInput.getFare(),
				riInput.getComments(),
				riInput.getEstimatedTripStartTime(),
				riInput.getEstimatedTripEndTime()				
				));
		return "Saved Successfully";
	}
	
}
