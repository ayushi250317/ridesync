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

		//this is a dummy entry in the location table that will be created for rider pickup points.
		int pickupLatLong = locationService.addLocation(new Location(
				0,
				0,
				"",
				"")).getLocationId();

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
				riInput.getEstimatedTripEndTime(),
				pickupLatLong
				)));

			return res;

	}

	public RideInfoResponse updateRideInfo(RideInfoInput riInput) {
		RideInfoResponse res = new RideInfoResponse();
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

		return res;		
	}

	public List<RideInfoResponse> getAllRideInfo(List<Ride> rides) {
		List<RideInfoResponse> res = new ArrayList<>();
		
		for(Ride r:rides) {
			RideInfo rideInfo = rideInfoRepository.findByRideIdAndUserId(r.getRideId(),r.getUserId());
			
			Location loc1 = locationService.findLocationById(rideInfo.getStartLocationId());
			Location loc2 = locationService.findLocationById(rideInfo.getEndLocationId());
			Location pickupLocation = locationService.findLocationById(rideInfo.getPickupLocationId());

			RideInfoResponse temp = new RideInfoResponse(loc1, loc2, rideInfo, pickupLocation);
			
			res.add(temp);
		}
		return res;
	}

	public RideInfoResponse updatePickupLocation(Integer rideId, Integer userId, Location pickup){
		RideInfoResponse res = new RideInfoResponse();
			res.setRideInfo(rideInfoRepository.findByRideIdAndUserId(rideId, userId));
			res.setPickupLocation(locationService.updateLocation(new Location(
					res.getRideInfo().getPickupLocationId(),
					pickup.getLattitude(),
					pickup.getLongitude(),
					pickup.getLandmark(),
					pickup.getAddress()
			)));

			res.setLocation1(locationService.findLocationById(res.getRideInfo().getStartLocationId()));
			res.setLocation2(locationService.findLocationById(res.getRideInfo().getEndLocationId()));

		return res;
	}

	public List<RideInfoResponse> getAllMembers(Integer RideId){

		List<RideInfoResponse> res = new ArrayList<>();
		List<RideInfo> members = rideInfoRepository.findByRideId(RideId);

		for(RideInfo member: members){
			RideInfoResponse temp = new RideInfoResponse(
					locationService.findLocationById(member.getStartLocationId()),
					locationService.findLocationById(member.getEndLocationId()),
					member,
					locationService.findLocationById(member.getPickupLocationId())
					);

			res.add(temp);
		}

		return res;

	}

    public RideInfoResponse getDriverLocation(Integer rideId) {
       return RideInfoResponse.builder().build();
    }

}
