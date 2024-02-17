package com.app.ridesync.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ridesync.dto.requests.RideInfoInput;
import com.app.ridesync.dto.responses.RideInfoResponse;
import com.app.ridesync.entities.Location;
import com.app.ridesync.entities.RideInfo;
import com.app.ridesync.repositories.RideInfoRepository;

@Service
public class RideInfoService {
	
	@Autowired
	private RideInfoRepository rideInfoRepository;
	@Autowired
	private LocationService locationService;
	
	public RideInfoResponse addRideInfo(RideInfoInput riInput) { // format for data that includes location.
		RideInfoResponse res = new RideInfoResponse();
		try {
		//add location 1
		res.setLocation1(locationService.addLocation(new Location(
								 riInput.getLattitude1(),
								 riInput.getLongitude1(),
								 riInput.getLandmark1(),
								 riInput.getAddress1()
								 )));
		
				
		//add location 2
		res.setLocation2(locationService.addLocation(new Location(
				 riInput.getLattitude2(),
				 riInput.getLongitude2(),
				 riInput.getLandmark2(),
				 riInput.getAddress2()
				 )));
		
		
		//get their Ids and then save RideInfo entry.
		res.setRideInfo(rideInfoRepository.save(new RideInfo(
				riInput.getRideId(),
				riInput.getUserId(),
				res.getLocation1().getLocationId(),
				res.getLocation2().getLocationId(),
				true,                               //isDriver is set to be true, here the driver only can post the ride. 
				riInput.getFare(),
				riInput.getComments(),
				riInput.getEstimatedTripStartTime(),
				riInput.getEstimatedTripEndTime()				
				)));
		}catch(Exception e) {
			res.setSuccess(false);
			res.setMessage(e.toString());
			return res;
		}
		res.setSuccess(true);
		res.setMessage("Details added in RideInfo Table!");
		return res;
	}
	
}
