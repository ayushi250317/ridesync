package com.app.ridesync.projections;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record RideInfoProjection(
		String riderName,
		Integer riderId,
		Boolean IsDriver,
		String startLocationAddress,
		String startLocationLandmark,
		Double startLat,
		Double startLong,
		String endLocationAddress,
		String endLocationLandmark,
		Double endLat,
		Double endLong,

		Double fare,
		String comments,
		Integer rating,

		//TODO: 
		LocalTime waitTime,  //has to be computed based on riderTripStartTime
		LocalDateTime riderTripStartTime,   //has to be changed at the database level to store this info rather than estimated trip start time
		LocalDateTime riderTripEndTime // same change as rider trip start time
) {}
