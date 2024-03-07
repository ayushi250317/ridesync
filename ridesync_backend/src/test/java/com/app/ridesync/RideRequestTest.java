package com.app.ridesync;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.app.ridesync.dto.requests.RideRequest;
import com.app.ridesync.dto.responses.RideRequestResponse;
import com.app.ridesync.entities.Location;
import com.app.ridesync.entities.RequestStatus;
import com.app.ridesync.entities.RideRequestInfo;
import com.app.ridesync.repositories.RideRequestRepository;
import com.app.ridesync.services.JwtService;
import com.app.ridesync.services.LocationService;
import com.app.ridesync.services.RideRequestService;

public class RideRequestTest {

    @Mock
    private LocationService locationService;

    @Mock
    private RideRequestRepository rideRequestRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private RideRequestService rideRequestService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRequestRide() {
        RideRequest rideRequest = new RideRequest();
        rideRequest.setLattitude1(1.0);
        rideRequest.setLongitude1(2.0);
        rideRequest.setLandmark1("Landmark 1");
        rideRequest.setAddress1("Address 1");
        rideRequest.setLattitude2(3.0);
        rideRequest.setLongitude2(4.0);
        rideRequest.setLandmark2("Landmark 2");
        rideRequest.setAddress2("Address 2");
        rideRequest.setRideId(123);
        rideRequest.setDriverId(456);
        rideRequest.setEstimatedTripStartTime(LocalDateTime.parse("2024-03-06T12:00:00"));

        String jwtToken="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIn0.uXy8l0MeVGqx2pUKqDb3ZWGqcJyO-o2BMLk4x6zRhkY";

        Location startLocation = new Location(rideRequest.getLattitude1(), rideRequest.getLongitude1(),
                rideRequest.getLandmark1(), rideRequest.getAddress1());
        Location endLocation = new Location(rideRequest.getLattitude2(), rideRequest.getLongitude2(),
                rideRequest.getLandmark2(), rideRequest.getAddress2());

        RideRequestInfo rideRequestInfo = RideRequestInfo.builder()
                .rideId(rideRequest.getRideId())
                .driverId(rideRequest.getDriverId())
                .riderId(jwtService.extractUserId(jwtToken))
                .requestStatus(RequestStatus.REQUESTED)
                .startLocationId(startLocation.getLocationId())
                .endLocationId(endLocation.getLocationId())
                .tripStartTime(rideRequest.getEstimatedTripStartTime())
                .build();

        when(locationService.addLocation(startLocation)).thenReturn(startLocation);
        when(locationService.addLocation(endLocation)).thenReturn(endLocation);
        RideRequestResponse response = rideRequestService.requestRide(jwtToken, rideRequest);
        assertTrue(response.isSuccess());
        assertEquals("Ride requested successfully", response.getMessage());
        verify(locationService, times(2)).addLocation(any(Location.class));
        verify(rideRequestRepository, times(1)).save(any(RideRequestInfo.class));
    }
}

