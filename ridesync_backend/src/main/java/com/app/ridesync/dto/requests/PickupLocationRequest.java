package com.app.ridesync.dto.requests;

import com.app.ridesync.entities.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PickupLocationRequest {

    Integer rideId;

    Location location;
}
