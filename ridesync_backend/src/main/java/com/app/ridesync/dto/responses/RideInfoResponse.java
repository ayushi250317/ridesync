package com.app.ridesync.dto.responses;

import com.app.ridesync.entities.Location;
import com.app.ridesync.entities.RideInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RideInfoResponse {

	private Location location1;
	private Location location2;
	private RideInfo rideInfo;
	private Location pickupLocation;

	public Location getLocation1() {
		return location1;
	}

	public void setLocation1(Location location1) {
		this.location1 = location1;
	}

	public Location getLocation2() {
		return location2;
	}

	public void setLocation2(Location location2) {
		this.location2 = location2;
	}

	public RideInfo getRideInfo() {
		return rideInfo;
	}

	public void setRideInfo(RideInfo rideInfo) {
		this.rideInfo = rideInfo;
	}

	public Location getPickupLocation() {
		return pickupLocation;
	}

	public void setPickupLocation(Location pickupLocation) {
		this.pickupLocation = pickupLocation;
	}

}
