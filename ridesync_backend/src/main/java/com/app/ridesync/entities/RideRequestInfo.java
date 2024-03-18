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
@Table(name = "rideRequest")
public class RideRequestInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer rideRequestId;
    private Integer riderId;
    private Integer driverId;
    private Integer rideId;
    private RequestStatus requestStatus;
    private Integer startLocationId;
	private Integer endLocationId;
    private LocalDateTime tripStartTime;
    private LocalDateTime createdTime;
}


