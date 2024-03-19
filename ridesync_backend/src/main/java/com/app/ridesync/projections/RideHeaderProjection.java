package com.app.ridesync.projections;

import java.time.LocalDateTime;

public record RideHeaderProjection (
	String description,
	LocalDateTime originalTripStartTime,
	LocalDateTime tripPostedTime,
	String status,
	Integer seatsAvailable,
	String startLocationAddress,
	String endLocationAddress,
	String rideVehicle
){}
