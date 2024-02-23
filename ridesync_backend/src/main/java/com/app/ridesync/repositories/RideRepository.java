package com.app.ridesync.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.ridesync.entities.Ride;
import com.app.ridesync.entities.User;
import com.app.ridesync.projections.RideHistoryProjection;

@Repository
public interface RideRepository extends JpaRepository<Ride, Integer> {

	List<User> findByUserId(Integer userId);

	Ride findByRideId(Integer rideId);

	List<Ride> findAllByUserId(Integer userId);

	@Query("SELECT "
			+ "NEW com.app.ridesync.projections.RideHistoryProjection(user.fullName AS userName, "
			+ "ride.description,ride.startTime AS originalTripStartTime, ride.status, "
			+ "vehicle.model AS rideVehicle, "
			+ "startLocation.address AS startLocationAddress, startLocation.landmark AS startLocationLandmark, "
			+ "endLocation.address AS endLocationAddress, endLocation.landmark AS endLocationLandmark, "
			+ "rideInfo.fare,rideInfo.comments,rideInfo.rating,rideInfo.waitTime,rideInfo.estimatedTripStartTime AS riderTripStartTime, rideInfo.estimatedTripEndTime AS riderTripEndTime) "
			+ "FROM RideInfo rideInfo "
			+ "JOIN Ride ride ON rideInfo.rideId = ride.rideId "
			+ "JOIN Vehicle vehicle ON ride.vehicleId = vehicle.vehicleId "
			+ "JOIN Location startLocation ON rideInfo.startLocationId = startLocation.locationId "
			+ "JOIN Location AS endLocation ON rideInfo.endLocationId = endLocation.locationId "
			+ "JOIN User user ON rideInfo.userId = user.userId "
			+ "WHERE rideInfo.userId = :userId")

	List<RideHistoryProjection> findByRideAndUserId(@Param("userId") Integer userId);

}
