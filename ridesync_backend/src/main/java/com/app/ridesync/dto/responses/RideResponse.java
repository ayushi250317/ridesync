package com.app.ridesync.dto.responses;

import com.app.ridesync.entities.Ride;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RideResponse {
	
	private Ride ride;
	private RideInfoResponse rideInfo;
	private String message;
    private boolean success;
}
