package com.app.ridesync.dto.requests;

import com.app.ridesync.entities.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PickupLocationRequest {

    Integer rideId;

    Location location;
}
