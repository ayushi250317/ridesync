package com.app.ridesync.projections;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RideHistoryProjection {
	private String userName;

	private String description;
	private LocalDateTime originalTripStartTime;

	private String status;
	private String rideVehicle;
	private String startLocationAddress;
	private String startLocationLandmark;
	private String endLocationAddress;
	private String endLocationLandmark;

	private double fare;
	private String comments;
	private Integer rating;

	//TODO: 
	private LocalDate waitTime;   //has to be computed based on riderTripStartTime & datatype has to be changed.
	private LocalDateTime riderTripStartTime;   //has to be changed at the database level to store this info rather than estimated trip start time
	private LocalDateTime riderTripEndTime; // same change as rider trip start time
}
