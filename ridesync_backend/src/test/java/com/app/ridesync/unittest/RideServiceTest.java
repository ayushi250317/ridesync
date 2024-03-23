package com.app.ridesync.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.app.ridesync.dto.requests.RideInfoInput;
import com.app.ridesync.dto.requests.RideInput;
import com.app.ridesync.dto.responses.GetRidesResponse;
import com.app.ridesync.dto.responses.RideInfoResponse;
import com.app.ridesync.dto.responses.RideResponse;
import com.app.ridesync.entities.GeoPoint;
import com.app.ridesync.entities.GeoPointRecord;
import com.app.ridesync.entities.Location;
import com.app.ridesync.entities.Ride;
import com.app.ridesync.entities.RideInfo;
import com.app.ridesync.projections.RideDetailProjection;
import com.app.ridesync.projections.RideHeaderProjection;
import com.app.ridesync.projections.RideHistoryProjection;
import com.app.ridesync.repositories.RideRepository;
import com.app.ridesync.services.GeoPointService;
import com.app.ridesync.services.RideInfoService;
import com.app.ridesync.services.RideService;
import com.google.maps.model.LatLng;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RideService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class RideServiceTest {
    @MockBean
    private GeoPointService geoPointService;

    @MockBean
    private RideInfoService rideInfoService;

    @MockBean
    private RideRepository rideRepository;

    @Autowired
    private RideService rideService;

    @Test
    void addRideTest() {


        Ride ride = new Ride();

        GeoPoint geopoint = new GeoPoint();
        geopoint.setGeoPointRecord(new GeoPointRecord(new ArrayList<>()));
        geopoint.setId(1);
        geopoint.setRide(ride);

        ride.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        ride.setDescription("The characteristics of someone or something");
        ride.setGeopoint(geopoint);
        ride.setOneTimePassword(1);
        ride.setRideId(1);
        ride.setSeatsAvailable(1);
        ride.setStartTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        ride.setStatus("Status");
        ride.setUserId(1);
        ride.setVehicleId(1);

        GeoPoint geopoint2 = new GeoPoint();
        geopoint2.setGeoPointRecord(new GeoPointRecord(new ArrayList<>()));
        geopoint2.setId(1);
        geopoint2.setRide(ride);

        Ride ride3 = new Ride();
        ride3.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        ride3.setDescription("The characteristics of someone or something");
        ride3.setGeopoint(geopoint2);
        ride3.setOneTimePassword(1);
        ride3.setRideId(1);
        ride3.setSeatsAvailable(1);
        ride3.setStartTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        ride3.setStatus("Status");
        ride3.setUserId(1);
        ride3.setVehicleId(1);
        when(rideRepository.save(Mockito.<Ride>any())).thenReturn(ride3);

        RideResponse actualAddRideResult = rideService.addRide(new RideInput());

        verify(rideRepository, atLeast(1)).save(Mockito.<Ride>any());
        GeoPointRecord geoPointRecord = actualAddRideResult.getRide()
                .getGeopoint()
                .getRide()
                .getGeopoint()
                .getGeoPointRecord();
        List<LatLng> expectedGeoPointsResult = geoPointRecord.getGeoPoints();
        assertSame(expectedGeoPointsResult, geoPointRecord.geoPoints());
    }


    @Test
    void getRidesTest() {
        ArrayList<RideInfoResponse> rideInfoResponseList = new ArrayList<>();
        when(rideInfoService.getAllRideInfo(Mockito.<List<Ride>>any())).thenReturn(rideInfoResponseList);
        when(rideRepository.findAllByUserId(Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        GetRidesResponse actualRides = rideService.getRides(1);

        verify(rideRepository).findAllByUserId(Mockito.<Integer>any());
        verify(rideInfoService).getAllRideInfo(Mockito.<List<Ride>>any());
        assertEquals("Successfully fetched Rides", actualRides.getMessage());
        assertTrue(actualRides.isSuccess());
        assertEquals(rideInfoResponseList, actualRides.getRides());
    }

    @Test
    void testUpdateRide() {
        Location location1 = new Location();
        location1.setAddress("42 Main St");
        location1.setLandmark("Landmark");
        location1.setLattitude(10.0d);
        location1.setLocationId(1);
        location1.setLongitude(10.0d);
        RideInfoResponse.RideInfoResponseBuilder location1Result = RideInfoResponse.builder().location1(location1);

        Location location2 = new Location();
        location2.setAddress("42 Main St");
        location2.setLandmark("Landmark");
        location2.setLattitude(10.0d);
        location2.setLocationId(1);
        location2.setLongitude(10.0d);
        RideInfoResponse.RideInfoResponseBuilder location2Result = location1Result.location2(location2);

        Location pickupLocation = new Location();
        pickupLocation.setAddress("42 Main St");
        pickupLocation.setLandmark("Landmark");
        pickupLocation.setLattitude(10.0d);
        pickupLocation.setLocationId(1);
        pickupLocation.setLongitude(10.0d);
        RideInfoResponse.RideInfoResponseBuilder pickupLocationResult = location2Result.pickupLocation(pickupLocation);

        RideInfo rideInfo = new RideInfo();
        rideInfo.setActive(true);
        rideInfo.setComments("Comments");
        rideInfo.setDriver(true);
        rideInfo.setEndLocationId(1);
        rideInfo.setEstimatedTripEndTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        rideInfo.setEstimatedTripStartTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        rideInfo.setFare(10.0d);
        rideInfo.setPickupLocationId(1);
        rideInfo.setRating(1);
        rideInfo.setRideId(1);
        rideInfo.setRideInfoId(1);
        rideInfo.setStartLocationId(1);
        rideInfo.setUserId(1);
        rideInfo.setWaitTime(LocalTime.MIDNIGHT);
        RideInfoResponse buildResult = pickupLocationResult.rideInfo(rideInfo).build();
        when(rideInfoService.updateRideInfo(Mockito.<RideInfoInput>any())).thenReturn(buildResult);

        Ride ride = new Ride();

        GeoPoint geopoint = new GeoPoint();
        geopoint.setGeoPointRecord(new GeoPointRecord(new ArrayList<>()));
        geopoint.setId(1);
        geopoint.setRide(ride);

        ride.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        ride.setDescription("The characteristics of someone or something");
        ride.setGeopoint(geopoint);
        ride.setOneTimePassword(1);
        ride.setRideId(1);
        ride.setSeatsAvailable(1);
        ride.setStartTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        ride.setStatus("Status");
        ride.setUserId(1);
        ride.setVehicleId(1);

        when(rideRepository.save(Mockito.<Ride>any())).thenReturn(ride);
        when(rideRepository.findByRideId(Mockito.<Integer>any())).thenReturn(ride);

        RideResponse actualUpdateRideResult = rideService.updateRide(new RideInput());

        verify(rideRepository).findByRideId(Mockito.<Integer>any());
        verify(rideInfoService).updateRideInfo(Mockito.<RideInfoInput>any());
        verify(rideRepository).save(Mockito.<Ride>any());
        assertEquals("Updated Ride Details", actualUpdateRideResult.getMessage());
        Ride ride7 = actualUpdateRideResult.getRide();
        assertNull(ride7.getVehicleId());
        assertNull(ride7.getDescription());
        assertNull(ride7.getStartTime());
        assertEquals(0, ride7.getSeatsAvailable().intValue());
        assertTrue(actualUpdateRideResult.isSuccess());
        assertSame(ride, ride7);
    }

    @Test
    void updateStatusTest() {
        Ride ride = new Ride();

        GeoPoint geopoint = new GeoPoint();
        geopoint.setGeoPointRecord(new GeoPointRecord(new ArrayList<>()));
        geopoint.setId(1);
        geopoint.setRide(ride);

        ride.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        ride.setDescription("The characteristics of someone or something");
        ride.setGeopoint(geopoint);
        ride.setOneTimePassword(1);
        ride.setRideId(1);
        ride.setSeatsAvailable(1);
        ride.setStartTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        ride.setStatus("Status");
        ride.setUserId(1);
        ride.setVehicleId(1);

        when(rideRepository.save(Mockito.<Ride>any())).thenReturn(ride);
        when(rideRepository.findByRideId(Mockito.<Integer>any())).thenReturn(ride);

        rideService.updateStatus(1, 1, "Status");

        verify(rideRepository).findByRideId(Mockito.<Integer>any());
        verify(rideRepository).save(Mockito.<Ride>any());
    }

    @Test
    void getRideHistoryProjectionByUserIdTest() {
        ArrayList<RideHistoryProjection> rideHistoryProjectionList = new ArrayList<>();
        when(rideRepository.findRidesByUserId(Mockito.<Integer>any())).thenReturn(rideHistoryProjectionList);

        List<RideHistoryProjection> actualRideHistoryProjectionByUserId = rideService.getRideHistoryProjectionByUserId(1);

        verify(rideRepository).findRidesByUserId(Mockito.<Integer>any());
        assertTrue(actualRideHistoryProjectionByUserId.isEmpty());
        assertSame(rideHistoryProjectionList, actualRideHistoryProjectionByUserId);
    }


    @Test
    void getRideDetailProjectionTest() {
        ArrayList<RideHeaderProjection> rideHeaderProjectionList = new ArrayList<>();
        when(rideRepository.findRideHeaderInfoByRideId(Mockito.<Integer>any())).thenReturn(rideHeaderProjectionList);
        when(rideRepository.findRideInfoByRideId(Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        RideDetailProjection actualRideDetailProjection = rideService.getRideDetailProjection(1);

        verify(rideRepository).findRideHeaderInfoByRideId(Mockito.<Integer>any());
        verify(rideRepository).findRideInfoByRideId(Mockito.<Integer>any());
        assertTrue(actualRideDetailProjection.rideProjection().isEmpty());
        assertEquals(rideHeaderProjectionList, actualRideDetailProjection.rideInfoProjection());
    }
}
