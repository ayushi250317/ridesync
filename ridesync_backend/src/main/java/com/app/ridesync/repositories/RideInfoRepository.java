package com.app.ridesync.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.app.ridesync.entities.RideInfo;

public interface RideInfoRepository extends JpaRepository<RideInfo, Integer> {
	Optional<RideInfo> findByRideId(Integer rideId);
	
	Optional<RideInfo> findByRideInfoId(Integer rideInfoId);
    
    Optional<RideInfo> findByUserId(Integer userId);
}
