package com.app.ridesync.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.ridesync.entities.RideInfo;

@Repository
public interface RideInfoRepository extends JpaRepository<RideInfo, Integer> {
	RideInfo findByRideId(Integer rideId);
	
	Optional<RideInfo> findByRideInfoId(Integer rideInfoId);
    
    Optional<RideInfo> findByUserId(Integer userId);

	RideInfo findByRideIdAndUserId(Integer userId, Integer rideId);
}
