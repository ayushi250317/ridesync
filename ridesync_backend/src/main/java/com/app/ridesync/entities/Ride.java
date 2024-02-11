package com.app.ridesync.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
@Table(name = "ride")
public class Ride {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long rideId;
	
	private LocalDateTime startTime;
	private LocalDateTime createdTime;
	private int oneTimePassword;
	private String status;
	private String description;
	private int seatsAvailable;
	private long vehicleId;
	
	public Ride(LocalDateTime startTime, LocalDateTime createdTime, int oneTimePassword, String status, String description,
			int seatsAvailable, long vehicleId) {
		super();
		this.startTime = startTime;
		this.createdTime = createdTime;
		this.oneTimePassword = oneTimePassword;
		this.status = status;
		this.description = description;
		this.seatsAvailable = seatsAvailable;
		this.vehicleId = vehicleId;
	}
	
	public long getRideId() {
		return rideId;
	}
	public void setRideId(long rideid) {
		rideId = rideid;
	}
	public LocalDateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalDateTime StartTime) {
		startTime = StartTime;
	}
	public LocalDateTime getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(LocalDateTime CreatedTime) {
		createdTime = CreatedTime;
	}
	public int getOneTimePassword() {
		return oneTimePassword;
	}
	public void setOneTimePassword(int oneTimePassword) {
		this.oneTimePassword = oneTimePassword;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getSeatsAvailable() {
		return seatsAvailable;
	}
	public void setSeatsAvailable(int seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
	}
	public long getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(long vehicleId) {
		this.vehicleId = vehicleId;
	}
	
	
}
