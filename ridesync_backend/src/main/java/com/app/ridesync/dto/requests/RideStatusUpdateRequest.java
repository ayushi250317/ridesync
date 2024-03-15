package com.app.ridesync.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RideStatusUpdateRequest {
    Integer rideId;
    String rideStatus;
}
