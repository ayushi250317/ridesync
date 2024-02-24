package com.app.ridesync.dto.requests;

import java.time.LocalDateTime;

import com.google.maps.model.LatLng;

public record RideSearchRequest(LatLng source, LatLng destination, LocalDateTime rideTime) {}
