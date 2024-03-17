package com.app.ridesync.entities;

import java.time.LocalDateTime;
import java.time.LocalTime;

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
	private Integer rideInfoId;
	private Integer rideId;
	private boolean isActive;
	private Integer userId;
	private Integer startLocationId;
	private Integer endLocationId;
	private boolean isDriver;
	private double fare;
	private Integer rating;
	private String comments;
	private LocalTime waitTime;
	private LocalDateTime estimatedTripStartTime;
	private LocalDateTime estimatedTripEndTime;
	private Integer pickupLocationId;                  //location for the riders pickup.

	public RideInfo(Integer rideId, Integer userId, Integer locationId1, Integer locationId2, boolean isDriver, double d,
			String comments, LocalDateTime estimatedTripStartTime, LocalDateTime estimatedTripEndTime, Integer pickupLocationId) {
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
		this.pickupLocationId = pickupLocationId;          //creates a dummy entry in the location table that must be updated.
	}

}
