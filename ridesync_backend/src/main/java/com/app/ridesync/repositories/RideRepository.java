package com.app.ridesync.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.ridesync.entities.Ride;
import com.app.ridesync.entities.User;
import com.app.ridesync.projections.SearchResultProjection;

@Repository
public interface RideRepository extends JpaRepository<Ride, Integer> {

	List<User> findByUserId(Integer userId);

	Ride findByRideId(Integer rideId);

	List<Ride> findAllByUserId(Integer userId);

	@Query("SELECT new com.app.ridesync.projections.SearchResultProjection(ride.rideId as rideId, ride.startTime, startLocation.address startLocationAddress, startLocation.landmark startLocationLandmark, endLocation.address endLocationAddress, endLocation.landmark endLocationLandmark, ride.createdTime, ride.status, ride.description, ride.seatsAvailable, vehicle.model AS rideVehicle)"
			+ " FROM Ride ride "
			+ " JOIN RideInfo rideInfo ON ride.rideId = rideInfo.rideId AND rideInfo.isDriver"
			+ " JOIN Location startLocation ON startLocation.locationId = rideInfo.startLocationId"
			+ " JOIN Location endLocation ON endLocation.locationId = rideInfo.endLocationId"
			+ " JOIN Vehicle vehicle on vehicle.vehicleId = ride.vehicleId"
			+ " WHERE ride.startTime >= :rideTimeStartLimit AND ride.startTime <= :rideTimeEndLimit AND ride.rideId IN :rideIds"
			+ " ORDER BY ride.startTime")
	List<SearchResultProjection>findByRideIds(@Param("rideIds") List<Integer> rideIds, @Param("rideTimeStartLimit") LocalDateTime rideTimeStartLimit, @Param("rideTimeEndLimit") LocalDateTime rideTimeEndLimit);

}
