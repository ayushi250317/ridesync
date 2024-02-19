package com.app.ridesync.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ridesync.dto.requests.RideInfoInput;
import com.app.ridesync.dto.responses.RideInfoResponse;
import com.app.ridesync.entities.Location;
import com.app.ridesync.entities.Ride;
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

	public RideInfoResponse updateRideInfo(RideInfoInput riInput) {
		RideInfoResponse res = new RideInfoResponse();
		try {
		RideInfo rideInfo = rideInfoRepository.findByRideIdAndUserId(riInput.getRideId(),riInput.getUserId());
		System.out.println(riInput.getUserId()+" "+ riInput.getRideId());
		System.out.println(rideInfo);
		rideInfo.setFare(riInput.getFare());
		rideInfo.setComments(riInput.getComments());
		
		res.setRideInfo(rideInfoRepository.save(rideInfo));
		
		
		res.setLocation1(locationService.updateLocation( new Location(
				rideInfo.getStartLocationId(),
				riInput.getLattitude1(),
				riInput.getLongitude1(),
				riInput.getLandmark1(),
				riInput.getAddress1()
				)));
		
		res.setLocation2(locationService.updateLocation(new Location(
				rideInfo.getEndLocationId(),
				riInput.getLattitude2(),
				riInput.getLongitude2(),
				riInput.getLandmark2(),
				riInput.getAddress2()
				)));
		
		
		rideInfo.getEndLocationId();
		}catch(Exception e) {
			res.setSuccess(false);
			res.setMessage(e.toString());
			return res;
		}
		res.setSuccess(true);
		res.setMessage("Update successfull!");
		return res;		
	}

	public List<RideInfoResponse> getAllRideInfo(List<Ride> rides) {
		List<RideInfoResponse> res = new ArrayList<>();
		
		for(Ride r:rides) {
			RideInfo rideInfo = rideInfoRepository.findByRideIdAndUserId(r.getRideId(), r.getUserId());
			
			Location loc1 = locationService.findLocationById(rideInfo.getStartLocationId());
			Location loc2 = locationService.findLocationById(rideInfo.getEndLocationId());
			
			RideInfoResponse temp = new RideInfoResponse(loc1, loc2, rideInfo, "Fetched Successfully", true);
			
			res.add(temp);
		}
		return res;
	}
	
}
