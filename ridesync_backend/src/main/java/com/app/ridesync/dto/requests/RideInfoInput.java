package com.app.ridesync.dto.requests;

import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RideInfoInput {
	
	private Integer userId;
	
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
	
	//RideInfo
	private Integer rideId; //--------------------generated (extract from ride Insertion)
	private double fare;
	private String comments;
	private LocalTime waitTime;
	private LocalDateTime estimatedTripStartTime;
	private LocalDateTime estimatedTripEndTime;
	
	public RideInfoInput(Integer userId, double d, double e, String landmark1, String address1, double f,
			double g, String landmark2, String address2, Integer rideId2, double h, String comments,
			LocalDateTime localDate, LocalDateTime localDate2) {
		super();
		this.userId = userId;
		this.lattitude1 = d;
		this.longitude1 = e;
		this.landmark1 = landmark1;
		this.address1 = address1;
		this.lattitude2 = f;
		this.longitude2 = g;
		this.landmark2 = landmark2;
		this.address2 = address2;
		this.rideId = rideId2;
		this.fare = h;
		this.comments = comments;
		this.estimatedTripStartTime = localDate;
		this.estimatedTripEndTime = localDate2;
	}

	public RideInfoInput(Integer userId, double lattitude1, double longitude1, String landmark1, String address1,
			double lattitude2, double longitude2, String landmark2, String address2, Integer rideId, double fare,
			String comments) {
		super();
		this.userId = userId;
		this.lattitude1 = lattitude1;
		this.longitude1 = longitude1;
		this.landmark1 = landmark1;
		this.address1 = address1;
		this.lattitude2 = lattitude2;
		this.longitude2 = longitude2;
		this.landmark2 = landmark2;
		this.address2 = address2;
		this.rideId = rideId;
		this.fare = fare;
		this.comments = comments;
	}		
}
