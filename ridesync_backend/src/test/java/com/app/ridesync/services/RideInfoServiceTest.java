package com.app.ridesync.services;

import com.app.ridesync.dto.requests.RideInfoInput;
import com.app.ridesync.dto.responses.RideInfoResponse;
import com.app.ridesync.entities.*;
import com.app.ridesync.repositories.RideInfoRepository;
import com.google.maps.model.LatLng;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static org.mockito.Mockito.*;

class RideInfoServiceTest {
    @Mock
    RideInfoRepository rideInfoRepository;
    @Mock
    LocationService locationService;
    @InjectMocks
    RideInfoService rideInfoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdatePickupLocation() {
        }

    @Test
    void testGetAllMembers() {
    }
}
