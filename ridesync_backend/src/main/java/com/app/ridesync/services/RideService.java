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
import com.app.ridesync.projections.RideDetailProjection;
import com.app.ridesync.projections.RideHeaderProjection;
import com.app.ridesync.projections.RideHistoryProjection;
import com.app.ridesync.projections.RideInfoProjection;
import com.app.ridesync.repositories.RideRepository;

@Service
public class RideService {

	@Autowired
	private RideRepository rideRepository;
	@Autowired
	private RideInfoService rideInfoService;	


	private final GeoPointService geoPointservice;

	@Autowired
	public RideService(GeoPointService geoPointservice, RideRepository rideRepository, RideInfoService rideInfoService) {
		this.geoPointservice = geoPointservice;
		this.rideRepository = rideRepository;
		this.rideInfoService = rideInfoService;
	}

	public RideResponse addRide(RideInput input){

		RideResponse res = new RideResponse();

		try {
			Random rand = new Random();
			Ride ride=new Ride();
			ride.setStartTime(input.getStartTime());
			ride.setCreatedTime(input.getCreatedTime());
			ride.setOneTimePassword(rand.nextInt());
			ride.setStatus("posted");
			ride.setDescription(input.getDescription());
			ride.setSeatsAvailable(input.getSeatsAvailable());
			ride.setVehicleId(input.getVehicleId());
			ride.setUserId(input.getUserId());

			res.setRide(rideRepository.save(ride));

			// Ride ride = new Ride(input.getStartTime(),input.getCreatedTime(),rand.nextInt(),"posted",                         
			// 		input.getDescription(),input.getSeatsAvailable(),input.getVehicleId(),input.getUserId());

			// res.setRide(rideRepository.save(ride));
			input.getRouteCoordinates().setRide(ride);

			geoPointservice.saveGeoPoints(input.getRouteCoordinates());

			RideInfoInput rideInfoInput=new RideInfoInput();
			rideInfoInput.setUserId(input.getUserId());
			rideInfoInput.setLattitude1(input.getLattitude1());
			rideInfoInput.setLongitude1(input.getLongitude1());
			rideInfoInput.setLandmark1(input.getLandmark1());
			rideInfoInput.setAddress1(input.getAddress1());
			rideInfoInput.setLattitude2(input.getLattitude2());
			rideInfoInput.setLongitude2(input.getLongitude2());
			rideInfoInput.setLandmark2(input.getLandmark2());
			rideInfoInput.setAddress2(input.getAddress2());
			rideInfoInput.setRideId(res.getRide().getRideId());
			rideInfoInput.setFare(input.getFare());
			rideInfoInput.setComments(input.getComments());
			rideInfoInput.setEstimatedTripStartTime(input.getEstimatedTripStartTime());
			rideInfoInput.setEstimatedTripEndTime(input.getEstimatedTripEndTime());
			//Save in RideInfo
			res.setRideInfo(rideInfoService.addRideInfo(rideInfoInput));
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
			RideInfoInput rideInfo=new RideInfoInput();
			rideInfo.setUserId(input.getUserId());
			rideInfo.setLattitude1(input.getLattitude1());
			rideInfo.setLattitude2(input.getLattitude2());
			rideInfo.setAddress1(input.getAddress1());
			rideInfo.setAddress2(input.getAddress2());
			rideInfo.setLongitude1(input.getLongitude1());
			rideInfo.setLongitude2(input.getLongitude2());
			rideInfo.setLandmark1(input.getLandmark1());
			rideInfo.setLandmark2(input.getLandmark2());
			rideInfo.setRideId(input.getRideId());
			rideInfo.setFare(input.getFare());
			rideInfo.setComments(input.getComments());
			res.setRideInfo(rideInfoService.updateRideInfo(rideInfo));                     //update the ride Info table.

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

	public void updateStatus(Integer rideId, Integer userId, String status){
		Ride ride = rideRepository.findByRideId(rideId);
		ride.setStatus(status);
		rideRepository.save(ride);
	}

	public List<RideHistoryProjection> getRideHistoryProjectionByUserId(Integer userId) {
		return rideRepository.findRidesByUserId(userId);	

	}

	public RideDetailProjection getRideDetailProjection(Integer rideId) {
		List<RideHeaderProjection> rideHeaderProjections=rideRepository.findRideHeaderInfoByRideId(rideId);
		List<RideInfoProjection> rideInfoProjections=rideRepository.findRideInfoByRideId(rideId)
		return new RideDetailProjection(rideHeaderProjections,rideInfoProjections);	

	}

}
