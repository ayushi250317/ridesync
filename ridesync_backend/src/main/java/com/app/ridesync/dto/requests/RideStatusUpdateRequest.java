package com.app.ridesync.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RideStatusUpdateRequest {
    Integer rideId;
    String rideStatus;
}
