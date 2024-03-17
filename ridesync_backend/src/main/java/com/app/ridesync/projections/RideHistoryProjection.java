package com.app.ridesync.projections;

import java.time.LocalDateTime;

public record RideHistoryProjection(
		Integer rideId,
		String riderName,
		Boolean isDriver,

		String description,
		LocalDateTime originalTripStartTime,
		LocalDateTime tripPostedTime,

		String status,
		String rideVehicle,
		String startLocationAddress,
		String startLocationLandmark,
		String endLocationAddress,
		String endLocationLandmark,

		Double fare
) {}
