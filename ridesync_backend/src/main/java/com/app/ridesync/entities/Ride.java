package com.app.ridesync.entities;

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
@Table(name = "ride")
public class Ride {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int rideId;
	
	private LocalDateTime startTime;
	private LocalDateTime createdTime;
	private Integer oneTimePassword;
	private String status;
	private String description;
	private Integer seatsAvailable;
	private Integer vehicleId;
	private Integer userId;
	
	public Ride(LocalDateTime startTime, LocalDateTime createdTime, int oneTimePassword, String status, String description,
			int seatsAvailable, Integer vehicleId, Integer userId) {
		super();
		this.startTime = startTime;
		this.createdTime = createdTime;
		this.oneTimePassword = oneTimePassword;
		this.status = status;
		this.description = description;
		this.seatsAvailable = seatsAvailable;
		this.vehicleId = vehicleId;
		this.userId = userId;
	}

}
