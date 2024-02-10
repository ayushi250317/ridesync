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
@Table(name = "ride")
public class Ride {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int rideId;
	
	private Date startTime;
	private Date createdTime;
	private int oneTimePassword;
	private String status;
	private String description;
	private int seatsAvailable;
	private int vehicleId;
	
	public Ride(Date startTime, Date createdTime, int oneTimePassword, String status, String description,
			int seatsAvailable, int vehicleId) {
		super();
		this.startTime = startTime;
		this.createdTime = createdTime;
		this.oneTimePassword = oneTimePassword;
		this.status = status;
		this.description = description;
		this.seatsAvailable = seatsAvailable;
		this.vehicleId = vehicleId;
	}
	
	public int getRideId() {
		return rideId;
	}
	public void setRideId(int rideid) {
		rideId = rideid;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date StartTime) {
		startTime = StartTime;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date CreatedTime) {
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
	public int getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}
	
	
}
