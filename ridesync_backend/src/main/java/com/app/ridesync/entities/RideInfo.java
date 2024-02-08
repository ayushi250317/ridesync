package com.app.ridesync.entities;

import java.util.Date;

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
	private int rideInfoId;
	private int rideId;
	private String isActive;
	private int userId;
	private int startLocationId;
	private int endLocationId;
	private String isDriver;
	private int fare;
	private int rating;
	private String comments;
	private Date waitTime;
	private Date estimatedTripStartTime;
	private Date estimatedTripEndTime;
	
	
	public int getRideInfoId() {
		return rideInfoId;
	}
	public void setRideInfoId(int rideInfoId) {
		this.rideInfoId = rideInfoId;
	}
	public int getRideId() {
		return rideId;
	}
	public void setRideId(int rideId) {
		this.rideId = rideId;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getStartLocationId() {
		return startLocationId;
	}
	public void setStartLocationId(int startLocationId) {
		this.startLocationId = startLocationId;
	}
	public int getEndLocationId() {
		return endLocationId;
	}
	public void setEndLocationId(int endLocationId) {
		this.endLocationId = endLocationId;
	}
	public String getIsDriver() {
		return isDriver;
	}
	public void setIsDriver(String isDriver) {
		this.isDriver = isDriver;
	}
	public int getFare() {
		return fare;
	}
	public void setFare(int fare) {
		this.fare = fare;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Date getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(Date waitTime) {
		this.waitTime = waitTime;
	}
	public Date getEstimatedTripStartTime() {
		return estimatedTripStartTime;
	}
	public void setEstimatedTripStartTime(Date estimatedTripStartTime) {
		this.estimatedTripStartTime = estimatedTripStartTime;
	}
	public Date getEstimatedTripEndTime() {
		return estimatedTripEndTime;
	}
	public void setEstimatedTripEndTime(Date estimatedTripEndTime) {
		this.estimatedTripEndTime = estimatedTripEndTime;
	}
	
	
	
}
