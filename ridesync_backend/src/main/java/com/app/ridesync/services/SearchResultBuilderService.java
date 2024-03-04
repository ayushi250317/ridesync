package com.app.ridesync.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ridesync.projections.SearchResultProjection;
import com.app.ridesync.repositories.RideRepository;

@Service
public class SearchResultBuilderService {
	private final RideRepository rideRepository;          

	@Autowired
	public SearchResultBuilderService(RideRepository rideRepository) {
		this.rideRepository = rideRepository;
	}

	public List<SearchResultProjection> buildSearchResults(List<Integer> rideIds, LocalDateTime rideTime) {
		return rideRepository.findByRideIds(rideIds, rideTime, rideTime.plusHours(12));		
	}
}
