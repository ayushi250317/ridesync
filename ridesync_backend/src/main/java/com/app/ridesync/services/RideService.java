package com.app.ridesync.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ridesync.dto.requests.RideInfoInput;
import com.app.ridesync.dto.requests.RideInput;
import com.app.ridesync.dto.responses.GetRidesResponse;
import com.app.ridesync.dto.responses.RideInfoResponse;
import com.app.ridesync.dto.responses.RideResponse;
import com.app.ridesync.entities.Ride;
import com.app.ridesync.projections.RideHistoryProjection;
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


	public RideResponse updateRide(RideInput input) {
		RideResponse res = new RideResponse();
		try {
			res.setRideInfo(rideInfoService.updateRideInfo(new RideInfoInput(
					input.getUserId(),
					input.getLattitude1(),
					input.getLattitude2(),
					input.getAddress1(),
					input.getAddress2(),
					input.getLongitude1(),
					input.getLongitude2(),
					input.getLandmark1(),
					input.getLandmark2(),
					input.getRideId(),
					input.getFare(),
					input.getComments()
					)));                     //update the ride Info table.

			Ride ride = rideRepository.findByRideId(input.getRideId());

			ride.setStartTime(input.getStartTime());
			ride.setDescription(input.getDescription());
			ride.setSeatsAvailable(input.getSeatsAvailable());
			ride.setVehicleId(input.getVehicleId());

			res.setRide(ride);

			rideRepository.save(ride);
		}catch(Exception e) {
			res.setMessage(e.toString());
			res.setSuccess(false);
			return res;

		}
		res.setMessage("Updated Ride Details");
		res.setSuccess(true);
		return res;
	}


	public GetRidesResponse getRides(Integer userId) {

		GetRidesResponse res = new GetRidesResponse();
		try {
			List<Ride> rides = rideRepository.findAllByUserId(userId);

			List<RideInfoResponse> rideInfos = rideInfoService.getAllRideInfo(rides);
			List<RideResponse> resValue = new ArrayList<>();

			for(int i=0;i<rides.size();i++) {
				resValue.add(new RideResponse(rides.get(i),rideInfos.get(i),"Fetch Successfull", true));
			}
			res.setRides(resValue);
		}catch(Exception e) {

			res.setMessage("RideService: "+e.toString());
			res.setSuccess(false);
			return res;
		}

		res.setMessage("Successfully fetched Rides");
		res.setSuccess(true);
		return res;
	}

	public List<RideHistoryProjection> getRideHistoryProjectionByUserId(Integer userId) {
		return rideRepository.findByRideAndUserId(userId);
	}

}
