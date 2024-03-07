package com.app.ridesync.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.ridesync.entities.RideRequestInfo;


@Repository
public interface RideRequestRepository extends JpaRepository<RideRequestInfo,Integer>{
    List<RideRequestInfo> findByRideId(Integer rideId);
    RideRequestInfo findByRideRequestId(Integer rideRequestId);

}