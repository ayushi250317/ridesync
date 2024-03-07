package com.app.ridesync.dto.requests;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RideRequest {
    private Integer rideId;
    private Integer driverId;

    //Location - 1 start
	private double lattitude1;
	private double longitude1;
	private String landmark1;
	private String address1;

	//Location - 2 end
	private double lattitude2;
	private double longitude2;
	private String landmark2;
	private String address2;

    private LocalDateTime estimatedTripStartTime;
	private String requestStatus;

}
