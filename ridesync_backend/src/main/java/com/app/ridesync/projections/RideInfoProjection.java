package com.app.ridesync.projections;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record RideInfoProjection(
		String riderName,
		Boolean IsDriver,

		String startLocationAddress,
		String startLocationLandmark,
		String endLocationAddress,
		String endLocationLandmark,

		Double fare,
		String comments,
		Integer rating,

		//TODO: 
		LocalTime waitTime,  //has to be computed based on riderTripStartTime
		LocalDateTime riderTripStartTime,   //has to be changed at the database level to store this info rather than estimated trip start time
		LocalDateTime riderTripEndTime // same change as rider trip start time
) {}
