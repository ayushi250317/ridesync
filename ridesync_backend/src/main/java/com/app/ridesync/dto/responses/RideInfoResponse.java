package com.app.ridesync.dto.responses;

import com.app.ridesync.entities.Location;
import com.app.ridesync.entities.RideInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RideInfoResponse {
	
	private Location location1;
	private Location location2;
	private RideInfo rideInfo;
	private String message;
    private boolean success;
}
