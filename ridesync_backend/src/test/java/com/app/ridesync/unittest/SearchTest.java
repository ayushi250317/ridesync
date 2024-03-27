package com.app.ridesync.unittest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.ridesync.entities.GeoPoint;
import com.app.ridesync.entities.GeoPointRecord;
import com.app.ridesync.entities.Ride;
import com.app.ridesync.projections.SearchResultProjection;
import com.app.ridesync.repositories.GeoPointRepository;
import com.app.ridesync.repositories.RideRepository;
import com.app.ridesync.services.RideSearchService;
import com.google.maps.model.LatLng;

@ExtendWith(MockitoExtension.class)
public class SearchTest {

	@Mock
	private RideRepository rideRepository;

	@Mock
	private GeoPointRepository geoPointRepository;

	@InjectMocks
	private RideSearchService rideSearchService;

	private static Ride ride;
	private static GeoPointRecord geoPointRecord;
	private static GeoPoint geoPoint;
	private static Integer userId;

	@BeforeAll
	static void setup() {		
		ride = new Ride();
		ride.setRideId(1);

		geoPointRecord = new GeoPointRecord(List.of(new LatLng(44.635360000000006,-63.595040000000004)));
		geoPoint = new GeoPoint();

		geoPoint.setGeoPointRecord(geoPointRecord);
		geoPoint.setRide(ride);
		
		userId = 1;
	}

	@Test
	void testFindRides_RetrievesRide() {		
		LatLng source = new LatLng(44.635360000000006, -63.595040000000004);
		LatLng destination = new LatLng(44.635360000000006, -63.595040000000004);
		LocalDateTime rideTime = LocalDateTime.now();

		when(geoPointRepository.findAll()).thenReturn(List.of(geoPoint));
		when(rideRepository.findRideDetailsByRideIds(List.of(1),rideTime,rideTime.plusHours(12),userId)).thenReturn(List.of());

		List<SearchResultProjection> rides=rideSearchService.findRides(userId,source,destination,rideTime);
		assertNotNull(rides);
		verify(geoPointRepository).findAll();
		verify(rideRepository).findRideDetailsByRideIds(List.of(1),rideTime,rideTime.plusHours(12),userId);

	}

	@Test
	void testFindRides_EmptyResult() {		
		LatLng source = new LatLng(70.635360000000006, -73.595040000000004);
		LatLng destination = new LatLng(44.635360000000006, -63.595040000000004);
		LocalDateTime rideTime = LocalDateTime.now();

		when(geoPointRepository.findAll()).thenReturn(List.of(geoPoint));
		when(rideRepository.findRideDetailsByRideIds(List.of(),rideTime,rideTime.plusHours(12),userId)).thenReturn(List.of());

		List<SearchResultProjection> rides=rideSearchService.findRides(userId, source,destination,rideTime);
		assertTrue(rides.isEmpty());
		verify(rideRepository).findRideDetailsByRideIds(List.of(),rideTime,rideTime.plusHours(12),userId);

	}
	
	@AfterEach
	void tearDown() {
		Mockito.reset(rideRepository);
		Mockito.reset(geoPointRepository);
	}
}
