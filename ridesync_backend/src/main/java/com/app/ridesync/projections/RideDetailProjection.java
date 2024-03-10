package com.app.ridesync.projections;

import java.util.List;

public record RideDetailProjection(
		List<RideHeaderProjection> rideProjection,
		List<RideInfoProjection> rideInfoProjection
) {}
