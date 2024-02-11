package com.app.ridesync.inputs;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RideInfoInput {
	
	private String userId;
	
	//Location - 1 start
//	private int locationId1; //--------------------generated
	private double lattitude1;
	private double longitude1;
	private String landmark1;
	private String address1;
	
	//Location - 2 end
//	private int locationId2;//--------------------generated
	private double lattitude2;
	private double longitude2;
	private String landmark2;
	private String address2;
	
	//RideInfo
//	private int rideInfoId; //--------------------generated
	private long rideId; //--------------------generated (extract from ride Insertion)
//	private String isActive;
//	private int startLocationId; //--------------------generated(extract from locationInsertion1)
//	private int endLocationId; //--------------------generated(extract from locationInsertion2)
//	private String isDriver;
	private double fare;
//	private int rating;
	private String comments;
	private LocalTime waitTime;
	private LocalDateTime estimatedTripStartTime;
	private LocalDateTime estimatedTripEndTime;
	
	public RideInfoInput(String userId, double d, double e, String landmark1, String address1, double f,
			double g, String landmark2, String address2, long rideId2, double h, String comments,
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
	
	
}
