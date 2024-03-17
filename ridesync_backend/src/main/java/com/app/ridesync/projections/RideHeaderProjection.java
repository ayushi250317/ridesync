package com.app.ridesync.projections;

import java.time.LocalDateTime;

import com.app.ridesync.entities.GeoPointRecord;

public record RideHeaderProjection (
	String description,
	LocalDateTime originalTripStartTime,
	LocalDateTime tripPostedTime,

	String status,
	String rideVehicle,
	GeoPointRecord routeCoordinates
){}
