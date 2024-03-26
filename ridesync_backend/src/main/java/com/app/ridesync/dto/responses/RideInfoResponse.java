package com.app.ridesync.dto.responses;

import com.app.ridesync.entities.Location;
import com.app.ridesync.entities.RideInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RideInfoResponse {
	
	private Location location1;
	private Location location2;
	private RideInfo rideInfo;
	private Location pickupLocation;

}
