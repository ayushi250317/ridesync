package com.app.ridesync.projections;

import java.time.LocalDateTime;

public record SearchResultProjection (	
	Integer rideId,
	LocalDateTime startTime,
	String startLocationAddress,
	String startLocationLandmark,
	String endLocationAddress,
	String endLocationLandmark,
	LocalDateTime createdTime,
	String status,
	String description,
	Integer seatsAvailable,
	String rideVehicle
) {}
