package com.app.ridesync.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ridesync.dto.requests.RideInfoInput;
import com.app.ridesync.dto.requests.RideInput;
import com.app.ridesync.dto.responses.RideResponse;
import com.app.ridesync.entities.Ride;
import com.app.ridesync.repositories.RideRepository;

@Service
public class RideService {
	
	@Autowired
	private RideRepository rideRepository;
	@Autowired
	private RideInfoService rideInfoService;

	
	public RideResponse addRide(RideInput input){
		
		RideResponse res = new RideResponse();
		
		try {
		Random rand = new Random();
		
		res.setRide(rideRepository.save(new Ride(
				input.getStartTime(),
				input.getCreatedTime(),
				rand.nextInt(1001, 9999),
				"posted",                         // can be posted/ active/ completed.
				input.getDescription(),
				input.getSeatsAvailable(),
				input.getVehicleId(),
				input.getUserId()
				)));
				 
		//Save in RideInfo
		res.setRideInfo(rideInfoService.addRideInfo(new RideInfoInput(
				input.getUserId(),
				input.getLattitude1(),
				input.getLongitude1(),
				input.getLandmark1(),
				input.getAddress1(),
				input.getLattitude2(),
				input.getLongitude2(),
				input.getLandmark2(),
				input.getAddress2(),
				res.getRide().getRideId(),  		//Extract the rideID from the res object.
				input.getFare(),
				input.getComments(),
				input.getEstimatedTripStartTime(),
				input.getEstimatedTripEndTime()
				)));
		}catch(Exception e) {
			res.setSuccess(false);
			res.setMessage(e.toString());
			return res;
		}
		
		res.setSuccess(true);
		res.setMessage("Ride Posted Successfully!");
		return res;
		
	}

}
