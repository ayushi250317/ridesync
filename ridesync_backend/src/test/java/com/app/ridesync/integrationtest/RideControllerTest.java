package com.app.ridesync.integrationtest;

import com.app.ridesync.controllers.RideController;
import com.app.ridesync.dto.requests.PickupLocationRequest;
import com.app.ridesync.dto.requests.RideInput;
import com.app.ridesync.dto.responses.ApiResponse;
import com.app.ridesync.dto.responses.GetRidesResponse;
import com.app.ridesync.dto.responses.RideInfoResponse;
import com.app.ridesync.dto.responses.RideResponse;
import com.app.ridesync.entities.*;
import com.app.ridesync.projections.RideDetailProjection;
import com.app.ridesync.projections.RideHeaderProjection;
import com.app.ridesync.projections.RideHistoryProjection;
import com.app.ridesync.projections.RideInfoProjection;
import com.app.ridesync.services.JwtService;
import com.app.ridesync.services.RideInfoService;
import com.app.ridesync.services.RideService;
import com.google.maps.model.LatLng;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static org.mockito.Mockito.*;

public class RideControllerTest {
    @Mock
    RideService rideService;
    @Mock
    RideInfoService rideInfoService;
    @Mock
    JwtService jwtService;
    @InjectMocks
    RideController rideController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addRideTest() throws Exception {
        when(rideService.addRide(any())).thenReturn(new RideResponse(new Ride(0, LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), Integer.valueOf(0), "status", "description", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), new GeoPoint(Integer.valueOf(0), new GeoPointRecord(List.of(new LatLng(0d, 0d))), null)), new RideInfoResponse(new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address"), new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address"), new RideInfo(Integer.valueOf(0), Integer.valueOf(0), true, Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), true, 0d, Integer.valueOf(0), "comments", LocalTime.of(19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), Integer.valueOf(0)), new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address")), "message", true));
        when(jwtService.extractUserId(anyString())).thenReturn(Integer.valueOf(0));

        RideResponse result = rideController.addRide("jwtToken", new RideInput(Integer.valueOf(0), Integer.valueOf(0), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), 0, "status", "description", 0, Integer.valueOf(0), 0d, 0d, "landmark1", "address1", 0d, 0d, "landmark2", "address2", "isActive", "isDriver", 0d, Integer.valueOf(0), "comments", LocalTime.of(19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), new GeoPoint(Integer.valueOf(0), new GeoPointRecord(List.of(new LatLng(0d, 0d))), new Ride(0, LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), Integer.valueOf(0), "status", "description", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), null))));
        Assert.assertEquals(new RideResponse(new Ride(0, LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), Integer.valueOf(0), "status", "description", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), new GeoPoint(Integer.valueOf(0), new GeoPointRecord(List.of(new LatLng(0d, 0d))), null)), new RideInfoResponse(new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address"), new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address"), new RideInfo(Integer.valueOf(0), Integer.valueOf(0), true, Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), true, 0d, Integer.valueOf(0), "comments", LocalTime.of(19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), Integer.valueOf(0)), new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address")), "message", true), result);
    }

    @Test
    public void updateRideTest() throws Exception {
        when(rideService.updateRide(any())).thenReturn(new RideResponse(new Ride(0, LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), Integer.valueOf(0), "status", "description", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), new GeoPoint(Integer.valueOf(0), new GeoPointRecord(List.of(new LatLng(0d, 0d))), null)), new RideInfoResponse(new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address"), new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address"), new RideInfo(Integer.valueOf(0), Integer.valueOf(0), true, Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), true, 0d, Integer.valueOf(0), "comments", LocalTime.of(19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), Integer.valueOf(0)), new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address")), "message", true));
        when(jwtService.extractUserId(anyString())).thenReturn(Integer.valueOf(0));

        ResponseEntity<ApiResponse<RideResponse>> result = rideController.updateRide("jwtToken", new RideInput(Integer.valueOf(0), Integer.valueOf(0), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), 0, "status", "description", 0, Integer.valueOf(0), 0d, 0d, "landmark1", "address1", 0d, 0d, "landmark2", "address2", "isActive", "isDriver", 0d, Integer.valueOf(0), "comments", LocalTime.of(19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), new GeoPoint(Integer.valueOf(0), new GeoPointRecord(List.of(new LatLng(0d, 0d))), new Ride(0, LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), Integer.valueOf(0), "status", "description", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), null))));
        Assert.assertEquals(new ResponseEntity<ApiResponse<RideResponse>>(new ApiResponse<RideResponse>(new RideResponse(new Ride(0, LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), Integer.valueOf(0), "status", "description", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), new GeoPoint(Integer.valueOf(0), new GeoPointRecord(List.of(new LatLng(0d, 0d))), null)), new RideInfoResponse(new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address"), new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address"), new RideInfo(Integer.valueOf(0), Integer.valueOf(0), true, Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), true, 0d, Integer.valueOf(0), "comments", LocalTime.of(19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), Integer.valueOf(0)), new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address")), "message", true), true, "Result set was retrieved successfully"), null, 200), result);
    }

    @Test
    public void getRideTest() throws Exception {
        when(rideService.getRides(anyInt())).thenReturn(new GetRidesResponse(List.of(new RideResponse(new Ride(0, LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), Integer.valueOf(0), "status", "description", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), new GeoPoint(Integer.valueOf(0), new GeoPointRecord(List.of(new LatLng(0d, 0d))), null)), new RideInfoResponse(new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address"), new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address"), new RideInfo(Integer.valueOf(0), Integer.valueOf(0), true, Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), true, 0d, Integer.valueOf(0), "comments", LocalTime.of(19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), Integer.valueOf(0)), new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address")), "message", true)), "message", true));

        GetRidesResponse result = rideController.getRide(Integer.valueOf(0));
        Assert.assertEquals(new GetRidesResponse(List.of(new RideResponse(new Ride(0, LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), Integer.valueOf(0), "status", "description", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), new GeoPoint(Integer.valueOf(0), new GeoPointRecord(List.of(new LatLng(0d, 0d))), null)), new RideInfoResponse(new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address"), new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address"), new RideInfo(Integer.valueOf(0), Integer.valueOf(0), true, Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), true, 0d, Integer.valueOf(0), "comments", LocalTime.of(19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), Integer.valueOf(0)), new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address")), "message", true)), "message", true), result);
    }

    @Test
    public void getRidesForUserTest() throws Exception {
        when(rideService.getRideHistoryProjectionByUserId(anyInt())).thenReturn(List.of(new RideHistoryProjection(Integer.valueOf(0), "riderName", Boolean.TRUE, "description", LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), "status", "rideVehicle", "startLocationAddress", "startLocationLandmark", "endLocationAddress", "endLocationLandmark", Double.valueOf(0))));

        ResponseEntity<ApiResponse<List<RideHistoryProjection>>> result = rideController.getRidesForUser(Integer.valueOf(0));
        Assert.assertEquals(new ResponseEntity<ApiResponse<List<RideHistoryProjection>>>(new ApiResponse<List<RideHistoryProjection>>(List.of(new RideHistoryProjection(Integer.valueOf(0), "riderName", Boolean.TRUE, "description", LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), "status", "rideVehicle", "startLocationAddress", "startLocationLandmark", "endLocationAddress", "endLocationLandmark", Double.valueOf(0))), true, "Result set was retrieved successfully"), null, 200), result);
    }

    @Test
    public void gpdatePickupLocationTest() throws Exception {
        when(rideInfoService.updatePickupLocation(anyInt(), anyInt(), any())).thenReturn(new RideInfoResponse(new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address"), new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address"), new RideInfo(Integer.valueOf(0), Integer.valueOf(0), true, Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), true, 0d, Integer.valueOf(0), "comments", LocalTime.of(19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), Integer.valueOf(0)), new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address")));
        when(jwtService.extractUserId(anyString())).thenReturn(Integer.valueOf(0));

        ResponseEntity<ApiResponse<RideInfoResponse>> result = rideController.updatePickupLocation("jwtToken", new PickupLocationRequest(Integer.valueOf(0), new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address")));
        Assert.assertEquals(new ResponseEntity<ApiResponse<RideInfoResponse>>(new ApiResponse<RideInfoResponse>(new RideInfoResponse(new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address"), new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address"), new RideInfo(Integer.valueOf(0), Integer.valueOf(0), true, Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), true, 0d, Integer.valueOf(0), "comments", LocalTime.of(19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), Integer.valueOf(0)), new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address")), true, "Update successful"), null, 200), result);
    }

    @Test
    public void getAllTripDetailsTest() throws Exception {
        when(rideInfoService.getAllMembers(anyInt())).thenReturn(List.of(new RideInfoResponse(new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address"), new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address"), new RideInfo(Integer.valueOf(0), Integer.valueOf(0), true, Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), true, 0d, Integer.valueOf(0), "comments", LocalTime.of(19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), Integer.valueOf(0)), new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address"))));

        ResponseEntity<ApiResponse<List<RideInfoResponse>>> result = rideController.getAllTripDetails(Integer.valueOf(0));
        Assert.assertEquals(new ResponseEntity<ApiResponse<List<RideInfoResponse>>>(new ApiResponse<List<RideInfoResponse>>(List.of(new RideInfoResponse(new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address"), new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address"), new RideInfo(Integer.valueOf(0), Integer.valueOf(0), true, Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), true, 0d, Integer.valueOf(0), "comments", LocalTime.of(19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), Integer.valueOf(0)), new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address"))), true, "Fetched successfully"), null, 200), result);
    }

    @Test
    public void getRideDetailTest() throws Exception {
        when(rideService.getRideDetailProjection(anyInt())).thenReturn(new RideDetailProjection(List.of(new RideHeaderProjection("description", LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), "status", Integer.valueOf(0), "startLocationAddress", "endLocationAddress", "rideVehicle")), List.of(new RideInfoProjection("riderName", Integer.valueOf(0), Boolean.TRUE, "startLocationAddress", "startLocationLandmark", Double.valueOf(0), Double.valueOf(0), "endLocationAddress", "endLocationLandmark", Double.valueOf(0), Double.valueOf(0), Double.valueOf(0), "comments", Integer.valueOf(0), LocalTime.of(19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37)))));

        ResponseEntity<ApiResponse<RideDetailProjection>> result = rideController.getRideDetail(Integer.valueOf(0));
        Assert.assertEquals(new ResponseEntity<ApiResponse<RideDetailProjection>>(new ApiResponse<RideDetailProjection>(new RideDetailProjection(List.of(new RideHeaderProjection("description", LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), "status", Integer.valueOf(0), "startLocationAddress", "endLocationAddress", "rideVehicle")), List.of(new RideInfoProjection("riderName", Integer.valueOf(0), Boolean.TRUE, "startLocationAddress", "startLocationLandmark", Double.valueOf(0), Double.valueOf(0), "endLocationAddress", "endLocationLandmark", Double.valueOf(0), Double.valueOf(0), Double.valueOf(0), "comments", Integer.valueOf(0), LocalTime.of(19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37)))), true, "Result set was retrieved successfully"), null, 200), result);
    }


    @Test
    public void getRiderLocationTest() throws Exception {
        when(rideInfoService.getDriverLocation(anyInt())).thenReturn(new RideInfoResponse(new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address"), new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address"), new RideInfo(Integer.valueOf(0), Integer.valueOf(0), true, Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), true, 0d, Integer.valueOf(0), "comments", LocalTime.of(19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), Integer.valueOf(0)), new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address")));

        ResponseEntity<ApiResponse<RideInfoResponse>> result = rideController.getRiderLocation(Integer.valueOf(0));
        Assert.assertEquals(new ResponseEntity<ApiResponse<RideInfoResponse>>(new ApiResponse<RideInfoResponse>(new RideInfoResponse(new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address"), new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address"), new RideInfo(Integer.valueOf(0), Integer.valueOf(0), true, Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), true, 0d, Integer.valueOf(0), "comments", LocalTime.of(19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), LocalDateTime.of(2024, Month.MARCH, 23, 19, 20, 37), Integer.valueOf(0)), new Location(Integer.valueOf(0), 0d, 0d, "landmark", "address")), true, "Result set was retrieved successfully"), null, 200), result);
    }
}