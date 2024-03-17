package com.app.ridesync.UnitTesting;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.app.ridesync.dto.requests.RideInfoInput;
import com.app.ridesync.dto.responses.RideInfoResponse;
import com.app.ridesync.entities.Location;
import com.app.ridesync.entities.RideInfo;
import com.app.ridesync.repositories.RideInfoRepository;
import com.app.ridesync.services.LocationService;
import com.app.ridesync.services.RideInfoService;

import java.time.LocalDate;
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

@ContextConfiguration(classes = {RideInfoService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class RideInfoServiceTest {
    @MockBean
    private LocationService locationService;

    @MockBean
    private RideInfoRepository rideInfoRepository;

    @Autowired
    private RideInfoService rideInfoService;


    @Test
    void testAddRideInfo() {
        // Arrange
        Location location = new Location();
        location.setAddress("42 Main St");
        location.setLandmark("Landmark");
        location.setLattitude(10.0d);
        location.setLocationId(1);
        location.setLongitude(10.0d);
        when(locationService.addLocation(Mockito.<Location>any())).thenReturn(location);

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
        when(rideInfoRepository.save(Mockito.<RideInfo>any())).thenReturn(rideInfo);

        // Act
        RideInfoResponse actualAddRideInfoResult = rideInfoService.addRideInfo(new RideInfoInput());

        // Assert
        verify(locationService, atLeast(1)).addLocation(Mockito.<Location>any());
        verify(rideInfoRepository).save(Mockito.<RideInfo>any());
        RideInfo rideInfo2 = actualAddRideInfoResult.getRideInfo();
        LocalTime expectedToLocalTimeResult = rideInfo2.getWaitTime();
        assertSame(expectedToLocalTimeResult, rideInfo2.getEstimatedTripStartTime().toLocalTime());
    }

    @Test
    void testUpdateRideInfo() {
        // Arrange
        Location location = new Location();
        location.setAddress("42 Main St");
        location.setLandmark("Landmark");
        location.setLattitude(10.0d);
        location.setLocationId(1);
        location.setLongitude(10.0d);
        when(locationService.updateLocation(Mockito.<Location>any())).thenReturn(location);

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

        RideInfo rideInfo2 = new RideInfo();
        rideInfo2.setActive(true);
        rideInfo2.setComments("Comments");
        rideInfo2.setDriver(true);
        rideInfo2.setEndLocationId(1);
        rideInfo2.setEstimatedTripEndTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        rideInfo2.setEstimatedTripStartTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        rideInfo2.setFare(10.0d);
        rideInfo2.setPickupLocationId(1);
        rideInfo2.setRating(1);
        rideInfo2.setRideId(1);
        rideInfo2.setRideInfoId(1);
        rideInfo2.setStartLocationId(1);
        rideInfo2.setUserId(1);
        rideInfo2.setWaitTime(LocalTime.MIDNIGHT);
        when(rideInfoRepository.save(Mockito.<RideInfo>any())).thenReturn(rideInfo2);
        when(rideInfoRepository.findByRideIdAndUserId(Mockito.<Integer>any(), Mockito.<Integer>any())).thenReturn(rideInfo);

        // Act
        RideInfoResponse actualUpdateRideInfoResult = rideInfoService.updateRideInfo(new RideInfoInput());

        // Assert
        verify(rideInfoRepository).findByRideIdAndUserId(Mockito.<Integer>any(), Mockito.<Integer>any());
        verify(locationService, atLeast(1)).updateLocation(Mockito.<Location>any());
        verify(rideInfoRepository).save(Mockito.<RideInfo>any());
        RideInfo rideInfo3 = actualUpdateRideInfoResult.getRideInfo();
        LocalTime expectedToLocalTimeResult = rideInfo3.getWaitTime();
        assertSame(expectedToLocalTimeResult, rideInfo3.getEstimatedTripStartTime().toLocalTime());
    }

    @Test
    void testGetAllRideInfo() {
        // Arrange, Act and Assert
        assertTrue(rideInfoService.getAllRideInfo(new ArrayList<>()).isEmpty());
    }

    @Test
    void testUpdatePickupLocation() {
        // Arrange
        Location location = new Location();
        location.setAddress("42 Main St");
        location.setLandmark("Landmark");
        location.setLattitude(10.0d);
        location.setLocationId(1);
        location.setLongitude(10.0d);

        Location location2 = new Location();
        location2.setAddress("42 Main St");
        location2.setLandmark("Landmark");
        location2.setLattitude(10.0d);
        location2.setLocationId(1);
        location2.setLongitude(10.0d);
        when(locationService.findLocationById(Mockito.<Integer>any())).thenReturn(location);
        when(locationService.updateLocation(Mockito.<Location>any())).thenReturn(location2);

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
        when(rideInfoRepository.findByRideIdAndUserId(Mockito.<Integer>any(), Mockito.<Integer>any())).thenReturn(rideInfo);

        Location pickup = new Location();
        pickup.setAddress("42 Main St");
        pickup.setLandmark("Landmark");
        pickup.setLattitude(10.0d);
        pickup.setLocationId(1);
        pickup.setLongitude(10.0d);

        // Act
        RideInfoResponse actualUpdatePickupLocationResult = rideInfoService.updatePickupLocation(1, 1, pickup);

        // Assert
        verify(rideInfoRepository).findByRideIdAndUserId(Mockito.<Integer>any(), Mockito.<Integer>any());
        verify(locationService, atLeast(1)).findLocationById(Mockito.<Integer>any());
        verify(locationService).updateLocation(Mockito.<Location>any());
        RideInfo rideInfo2 = actualUpdatePickupLocationResult.getRideInfo();
        LocalTime expectedToLocalTimeResult = rideInfo2.getWaitTime();
        assertSame(expectedToLocalTimeResult, rideInfo2.getEstimatedTripStartTime().toLocalTime());
    }

    @Test
    void testGetAllMembers() {
        // Arrange
        when(rideInfoRepository.findByRideId(Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        // Act
        List<RideInfoResponse> actualAllMembers = rideInfoService.getAllMembers(1);

        // Assert
        verify(rideInfoRepository).findByRideId(Mockito.<Integer>any());
        assertTrue(actualAllMembers.isEmpty());
    }
}
