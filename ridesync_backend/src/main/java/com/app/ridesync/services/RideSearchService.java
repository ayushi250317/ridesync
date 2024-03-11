package com.app.ridesync.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ridesync.entities.GeoPoint;
import com.app.ridesync.projections.SearchResultProjection;
import com.app.ridesync.repositories.GeoPointRepository;
import com.app.ridesync.repositories.RideRepository;
import com.google.maps.model.LatLng;
import com.mapbox.geojson.Point;
import com.mapbox.turf.TurfClassification;
import com.mapbox.turf.TurfMeasurement;

@Service
public class RideSearchService {	
	private final GeoPointRepository geoPointRepo;
	private final RideRepository rideRepository;

	private static final float WITHIN_LIMIT = 1.5f;
	private List<Integer>filteredRides; 

	@Autowired
	public RideSearchService(GeoPointRepository geoPointRepo, RideRepository rideRepository) {
		this.geoPointRepo = geoPointRepo;
		this.rideRepository = rideRepository;
	}

	public  List<SearchResultProjection> findRides(LatLng source, LatLng destination, LocalDateTime rideTime) {
		return filterRides(geoPointRepo.findAll(),
				Point.fromLngLat(source.lng,source.lat),
				Point.fromLngLat(destination.lng,destination.lat),
				rideTime);

	}

	private  List<SearchResultProjection> filterRides(List<GeoPoint> geoPoints, Point source, Point destination, LocalDateTime rideTime) {
		filteredRides = new ArrayList<>();
		
		for(GeoPoint geoPoint : geoPoints) {
			List<Point> points = geoPoint.getGeoPointRecord()
					.getGeoPoints()
					.stream()
					.map(latLng ->  Point.fromLngLat(latLng.lng,latLng.lat))
					.collect(Collectors.toList());

			Point nearestPointToSource = TurfClassification.nearestPoint(source, points);
			Point nearestPointToDestination = TurfClassification.nearestPoint(destination, points);

			if(isValid(nearestPointToSource, nearestPointToDestination, source, destination))
				filteredRides.add(geoPoint.getRide().getRideId());
			
		}

		return findRideDetailsByFilteredRideIds(filteredRides, rideTime);
	}
	

	private List<SearchResultProjection> findRideDetailsByFilteredRideIds(List<Integer> rideIds, LocalDateTime rideTime) {
		return rideRepository.findRideDetailsByRideIds(rideIds, rideTime, rideTime.plusHours(12));		
	}

	private boolean isValid(Point foundSrc, Point foundDes, Point src, Point des) {
		return TurfMeasurement.distance(foundSrc, src) < WITHIN_LIMIT && TurfMeasurement.distance(foundDes, des) < WITHIN_LIMIT;
	}
}
