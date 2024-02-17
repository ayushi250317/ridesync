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
	private long rideId;
	
	private LocalDateTime startTime;
	private LocalDateTime createdTime;
	private int oneTimePassword;
	private String status;
	private String description;
	private int seatsAvailable;
	private long vehicleId;
	private String userId;
	
	public Ride(LocalDateTime startTime, LocalDateTime createdTime, int oneTimePassword, String status, String description,
			int seatsAvailable, long vehicleId, String userId) {
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
