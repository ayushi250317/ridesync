package com.app.ridesync.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.ridesync.entities.RideInfo;
import com.app.ridesync.entities.RideRequestInfo;
import com.app.ridesync.projections.RideRequestProjection;


@Repository
public interface RideRequestRepository extends JpaRepository<RideRequestInfo,Integer>{
    @Query("SELECT new com.app.ridesync.projections.RideRequestProjection(rideRequest.rideRequestId as rideRequestId, rideRequest.rideId as rideId,rideRequest.riderId as riderId,rideRequest.driverId as driverId,rideRequest.tripStartTime, rideRequest.requestStatus as requestStatus, pickupLocation.address as pickupAddress, pickupLocation.lattitude as pickupLat, pickupLocation.longitude as pickupLong, dropLocation.address as dropAddress, dropLocation.lattitude as dropLat, dropLocation.longitude as dropLong, user.fullName as riderName)"
			+ " FROM RideRequestInfo rideRequest "
			+ " JOIN Location pickupLocation ON pickupLocation.locationId = rideRequest.startLocationId"
			+ " JOIN Location dropLocation ON dropLocation.locationId = rideRequest.endLocationId"
			+ " JOIN User user ON user.userId=rideRequest.riderId"
			+ " WHERE  rideRequest.rideId=:rideId"
			)
    List<RideRequestProjection> findByRideId(@Param("rideId") Integer rideId);
    RideRequestInfo findByRideRequestId(Integer rideRequestId);

}