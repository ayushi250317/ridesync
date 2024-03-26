package com.app.ridesync.dto.responses;

import com.app.ridesync.entities.Ride;

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
public class RideResponse {
	
	private Ride ride;
	private RideInfoResponse rideInfo;
	private String message;
    private boolean success;
}
