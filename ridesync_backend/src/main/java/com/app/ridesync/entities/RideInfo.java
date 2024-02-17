package com.app.ridesync.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rideInfo")
public class RideInfo {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long rideInfoId;
	private long rideId;
	private boolean isActive;
	private String userId;
	private long startLocationId;
	private long endLocationId;
	private boolean isDriver;
	private double fare;
	private int rating;
	private String comments;
	private LocalDate waitTime;
	private LocalDateTime estimatedTripStartTime;
	private LocalDateTime estimatedTripEndTime;
	
	public RideInfo(long rideId, String userId, long locationId1, long locationId2, boolean isDriver, double d,
			String comments, LocalDateTime estimatedTripStartTime, LocalDateTime estimatedTripEndTime) {
		super();
		this.rideId = rideId;
		this.userId = userId;
		this.startLocationId = locationId1;
		this.endLocationId = locationId2;
		this.isDriver = isDriver;                              // the isDriver attribute must be set based on the calling function.
		this.fare = d;
		this.comments = comments;
		this.estimatedTripStartTime = estimatedTripStartTime;
		this.estimatedTripEndTime = estimatedTripEndTime;
		this.isActive = false;                             // the ride is initially to be false every time a new one is created.
		this.rating = 0;                                   // the ride must have a 0 rating initially unless the user changes it.
	}

}
