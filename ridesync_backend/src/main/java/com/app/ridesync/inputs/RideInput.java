package com.app.ridesync.inputs;

import java.time.LocalDate;
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
public class RideInput{
	
	private String userId; //-----------------common userId
	
	//Document
	
//	private int documentId; //--------------------generated
	private String documentName;
	private String documentType;
	private LocalDate expiryDate;
	
	
	//vehicle
//	private int vehicleId; //--------------------generated
//	private int documentId; //Insurance Document. (extract from the document insertion)
	private String regNo;
	private String model;
	private String make;
	private String type;
	
	//Ride
//	private int rideId; //--------------------generated
	private LocalDateTime startTime;
	private LocalDateTime createdTime;
//	private int oneTimePassword; //--------------------generated
	private String status;
	private String description;
	private int seatsAvailable;
//	private int vehicleId;	//--------------------generated (extract from the vehicle insertion)
	
	//Location - 1 start
//	private int locationId1; //--------------------generated
	private int lattitude1;
	private int longitude1;
	private String landmark1;
	private String address1;
	
	//Location - 2 end
//	private int locationId2;//--------------------generated
	private int lattitude2;
	private int longitude2;
	private String landmark2;
	private String address2;
	
	//RideInfo
//	private int rideInfoId; //--------------------generated
//	private int rideId; //--------------------generated (extract from ride Insertion)
	private String isActive;
//	private int startLocationId; //--------------------generated(extract from locationInsertion1)
//	private int endLocationId; //--------------------generated(extract from locationInsertion2)
	private String isDriver;
	private int fare;
	private int rating;
	private String comments;
	private LocalTime waitTime;
	private LocalDateTime estimatedTripStartTime;
	private LocalDateTime estimatedTripEndTime;	
	
		
}
