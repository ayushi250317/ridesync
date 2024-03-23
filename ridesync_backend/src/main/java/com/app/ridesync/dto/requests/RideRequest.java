package com.app.ridesync.dto.requests;

import java.time.LocalDateTime;

import com.app.ridesync.entities.RequestStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RideRequest {
    public void setRequestStatus(RequestStatus requestStatus) {
		this.requestStatus = requestStatus;
	}
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
	private RequestStatus requestStatus;

}
