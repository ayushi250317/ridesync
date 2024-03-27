package com.app.ridesync.services;

import java.util.ArrayList;
import java.util.List;

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

		// Location location1=new Location();
		// location1.setAddress(riInput.getAddress1());
		// location1.setLandmark(riInput.getLandmark1());
		// location1.setLattitude(riInput.getLattitude1());
		// location1.setLongitude(riInput.getLongitude1());
		// res.setLocation1(locationService.addLocation(location1));

		// Location location2=new Location();
		// location2.setAddress(riInput.getAddress2());
		// location2.setLandmark(riInput.getLandmark2());
		// location2.setLattitude(riInput.getLattitude2());
		// location2.setLongitude(riInput.getLongitude2());
		// res.setLocation2(locationService.addLocation(location2));

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


		// this is a dummy entry in the location table that will be created for rider
		// pickup points.
		int pickupLatLong = locationService.addLocation(new Location(
				0,
				0,
				"",
				"")).getLocationId();

		// get their Ids and then save RideInfo entry.
		RideInfo rideInfo = new RideInfo();
		rideInfo.setRideId(riInput.getRideId());
		rideInfo.setUserId(riInput.getUserId());
		rideInfo.setStartLocationId(res.getLocation1().getLocationId());
		rideInfo.setEndLocationId(res.getLocation2().getLocationId());
		rideInfo.setDriver(true);
		rideInfo.setActive(true);
		rideInfo.setFare(riInput.getFare());
		rideInfo.setComments(riInput.getComments());
		rideInfo.setEstimatedTripStartTime(riInput.getEstimatedTripStartTime());
		rideInfo.setEstimatedTripEndTime(riInput.getEstimatedTripEndTime());
		rideInfo.setPickupLocationId(pickupLatLong);
		res.setRideInfo(rideInfoRepository.save(rideInfo));
		return res;

	}

	public RideInfoResponse updateRideInfo(RideInfoInput riInput) {
		RideInfoResponse res = new RideInfoResponse();
		RideInfo rideInfo = rideInfoRepository.findByRideIdAndUserId(riInput.getRideId(), riInput.getUserId());
		System.out.println(riInput.getUserId() + " " + riInput.getRideId());
		System.out.println(rideInfo);
		rideInfo.setFare(riInput.getFare());
		rideInfo.setComments(riInput.getComments());

		res.setRideInfo(rideInfoRepository.save(rideInfo));

		Location location1=new Location();
		location1.setAddress(riInput.getAddress1());
		location1.setLandmark(riInput.getLandmark1());
		location1.setLattitude(riInput.getLattitude1());
		location1.setLongitude(riInput.getLongitude1());

		Location location2=new Location();
		location2.setAddress(riInput.getAddress2());
		location2.setLandmark(riInput.getLandmark2());
		location2.setLattitude(riInput.getLattitude2());
		location2.setLongitude(riInput.getLongitude2());

		res.setLocation1(locationService.updateLocation(location1));

		res.setLocation2(locationService.updateLocation(location2));

		return res;
	}

	public RideInfoResponse updatePickupLocation(Integer rideId, Integer userId, Location pickup) {
		RideInfoResponse res = new RideInfoResponse();
		res.setRideInfo(rideInfoRepository.findByRideIdAndUserId(rideId, userId));
		Location pickupLocation=new Location();
		pickupLocation.setLocationId(res.getRideInfo().getPickupLocationId());
		pickupLocation.setAddress(pickup.getAddress());
		pickupLocation.setLandmark(pickup.getLandmark());
		pickupLocation.setLattitude(pickup.getLattitude());
		pickupLocation.setLongitude(pickup.getLongitude());
		res.setPickupLocation(locationService.updateLocation(pickupLocation));

		res.setLocation1(locationService.findLocationById(res.getRideInfo().getStartLocationId()));
		res.setLocation2(locationService.findLocationById(res.getRideInfo().getEndLocationId()));

		return res;
	}

	public List<RideInfoResponse> getAllMembers(Integer RideId) {

		List<RideInfoResponse> res = new ArrayList<>();
		List<RideInfo> members = rideInfoRepository.findByRideId(RideId);

		for (RideInfo member : members) {
			RideInfoResponse temp = new RideInfoResponse();
			temp.setLocation1(locationService.findLocationById(member.getStartLocationId()));
			temp.setLocation2(locationService.findLocationById(member.getEndLocationId()));
			temp.setRideInfo(member);
			temp.setPickupLocation(locationService.findLocationById(member.getPickupLocationId()));
			res.add(temp);
		}

		return res;

	}

	public RideInfoResponse getDriverLocation(Integer rideId) {
		RideInfo rideInfo = rideInfoRepository.findByRideIdAndIsDriver(rideId, true);
		Location pickupLocation = locationService.findLocationById(rideInfo.getPickupLocationId());
		return RideInfoResponse.builder().pickupLocation(pickupLocation).build();
	}

}
