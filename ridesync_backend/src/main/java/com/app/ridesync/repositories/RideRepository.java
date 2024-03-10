package com.app.ridesync.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.ridesync.entities.Ride;
import com.app.ridesync.entities.User;
import com.app.ridesync.projections.RideHeaderProjection;
import com.app.ridesync.projections.RideHistoryProjection;
import com.app.ridesync.projections.RideInfoProjection;
import com.app.ridesync.projections.SearchResultProjection;

@Repository
public interface RideRepository extends JpaRepository<Ride, Integer> {

	List<User> findByUserId(Integer userId);

	Ride findByRideId(Integer rideId);

	List<Ride> findAllByUserId(Integer userId);

	@Query("SELECT "
			+ "NEW com.app.ridesync.projections.RideHistoryProjection(user.fullName AS riderName, rideInfo.isDriver, "
			+ "ride.description,ride.startTime AS originalTripStartTime, ride.createdTime as tripPostedTime, ride.status, "
			+ "vehicle.model AS rideVehicle, "
			+ "startLocation.address AS startLocationAddress, startLocation.landmark AS startLocationLandmark, "
			+ "endLocation.address AS endLocationAddress, endLocation.landmark AS endLocationLandmark, "
			+ "rideInfo.fare) "
			+ "FROM RideInfo rideInfo "
			+ "JOIN Ride ride ON rideInfo.rideId = ride.rideId "
			+ "JOIN Vehicle vehicle ON ride.vehicleId = vehicle.vehicleId "
			+ "JOIN Location startLocation ON rideInfo.startLocationId = startLocation.locationId "
			+ "JOIN Location AS endLocation ON rideInfo.endLocationId = endLocation.locationId "
			+ "JOIN User user ON rideInfo.userId = user.userId "
			+ "WHERE rideInfo.userId = :userId")

	List<RideHistoryProjection> findRidesByUserId(@Param("userId") Integer userId);

	@Query ("SELECT "
			+ "NEW com.app.ridesync.projections.RideInfoProjection(user.fullName AS riderName, rideInfo.isDriver, "
			+ "startLocation.address AS startLocationAddress, startLocation.landmark AS startLocationLandmark, "
			+ "endLocation.address AS endLocationAddress, endLocation.landmark AS endLocationLandmark, "
			+ "rideInfo.fare,rideInfo.comments,rideInfo.rating,rideInfo.waitTime,rideInfo.estimatedTripStartTime AS riderTripStartTime, rideInfo.estimatedTripEndTime AS riderTripEndTime) "

			+ "FROM RideInfo rideInfo "
			+ "JOIN Location startLocation ON rideInfo.startLocationId = startLocation.locationId "
			+ "JOIN Location AS endLocation ON rideInfo.endLocationId = endLocation.locationId "
			+ "JOIN User user ON rideInfo.userId = user.userId "
			+ "WHERE rideInfo.rideId = :rideId")
	List<RideInfoProjection> findRideInfoByRideId(@Param("rideId") Integer rideId);

	@Query ("SELECT "
			+ "NEW com.app.ridesync.projections.RideHeaderProjection(ride.description,ride.startTime AS originalTripStartTime, "
			+ "ride.createdTime as tripPostedTime, ride.status, "
			+ "vehicle.model AS rideVehicle, "
			+ "geoPoint.geoPointRecord as routeCoordinates) "		
			+ "FROM Ride ride "
			+ "JOIN Vehicle vehicle on vehicle.vehicleId = ride.vehicleId "
			+ "JOIN ride.geopoint geoPoint "
			+ "WHERE ride.rideId = :rideId")

	List<RideHeaderProjection> findRideHeaderInfoByRideId(@Param("rideId") Integer rideId);

	
	@Query("SELECT new com.app.ridesync.projections.SearchResultProjection(ride.rideId as rideId,rideInfo.userId as driverId,ride.startTime, startLocation.address startLocationAddress, startLocation.landmark startLocationLandmark, endLocation.address endLocationAddress, endLocation.landmark endLocationLandmark, ride.createdTime, ride.status, ride.description, ride.seatsAvailable, vehicle.model AS rideVehicle)"
			+ " FROM Ride ride "
			+ " JOIN RideInfo rideInfo ON ride.rideId = rideInfo.rideId AND rideInfo.isDriver"
			+ " JOIN Location startLocation ON startLocation.locationId = rideInfo.startLocationId"
			+ " JOIN Location endLocation ON endLocation.locationId = rideInfo.endLocationId"
			+ " JOIN Vehicle vehicle on vehicle.vehicleId = ride.vehicleId"
			+ " WHERE ride.status != 'completed' AND ride.startTime >= :rideTimeStartLimit AND ride.startTime <= :rideTimeEndLimit AND ride.rideId IN :rideIds"
			+ " ORDER BY ride.startTime")
	List<SearchResultProjection>findByRideIds(@Param("rideIds") List<Integer> rideIds, @Param("rideTimeStartLimit") LocalDateTime rideTimeStartLimit, @Param("rideTimeEndLimit") LocalDateTime rideTimeEndLimit);
}
