package com.app.ridesync.projections;

import java.time.LocalDateTime;

import com.app.ridesync.entities.RequestStatus;

public record RideRequestProjection(
    Integer rideRequestId,
    Integer rideId,
    Integer riderId,
    Integer driverId,
    LocalDateTime tripStartTime,
    RequestStatus requestStatus,
    String pickupAddress,
    Double pickupLat,
    Double pickupLong,
    String dropAddress,
    Double dropLat,
    Double dropLong,
    String riderName
) {
}
