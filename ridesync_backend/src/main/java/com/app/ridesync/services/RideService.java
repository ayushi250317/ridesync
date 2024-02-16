package com.app.ridesync.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ridesync.dto.requests.RideInfoInput;
import com.app.ridesync.dto.requests.RideInput;
import com.app.ridesync.entities.Document;
import com.app.ridesync.entities.Ride;
import com.app.ridesync.entities.Vehicle;
import com.app.ridesync.repositories.RideRepository;

@Service
public class RideService {
	
	@Autowired
	private RideRepository rideRepository;
	@Autowired
	private RideInfoService rideInfoService;
	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private DocumentService documentService;

	
	public String addRide(RideInput input){
		
		//Save Insurance Document
		//String documentName, int userId, String documentType, Date expiryDate
//		long documentId = documentService.addDocument(new Document(
//				input.getDocumentName(), 
//				input.getUserId(), 
//				input.getDocumentType(),
//				input.getExpiryDate()
//				));
//		
//		//Save first Vehicle 
//		//String regNo, int documentId, String model, String make, String type, int userId
//		long vehicleId = vehicleService.addVehicle(new Vehicle(
//				input.getRegNo(),
//				documentId,
//				input.getModel(),
//				input.getMake(),
//				input.getType(),
//				input.getUserId()				
//				));
//		
//		//Save Ride
//		//Date startTime, Date createdTime, int oneTimePassword, String status, String description, int seatsAvailable, int vehicleId
//		Ride ride = rideRepository.save(new Ride(
//				input.getStartTime(),
//				input.getCreatedTime(),
//				50001, //-------------------------------------OTP(TBD)
//				input.getStatus(),
//				input.getDescription(),
//				input.getSeatsAvailable(),
//				vehicleId
//				));
//		
//		long rideId = ride.getRideId();
//		 
//		//Save in RideInfo
//		rideInfoService.addRideInfo(new RideInfoInput(
//				input.getUserId(),
//				input.getLattitude1(),
//				input.getLongitude1(),
//				input.getLandmark1(),
//				input.getAddress1(),
//				input.getLattitude2(),
//				input.getLongitude2(),
//				input.getLandmark2(),
//				input.getAddress2(),
//				
//				rideId,
//				input.getFare(),
//				input.getComments(),
//				input.getEstimatedTripStartTime(),
//				input.getEstimatedTripEndTime()
//				));
//		
		return "Under Maintenance";
		
	}
	
	public Ride getRideById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
