package com.app.ridesync.integrationtest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.app.ridesync.controllers.RideRequestController;
import com.app.ridesync.dto.requests.AuthenticationRequest;
import com.app.ridesync.dto.requests.RegisterRequest;
import com.app.ridesync.dto.requests.RideInfoInput;
import com.app.ridesync.dto.requests.RideRequest;
import com.app.ridesync.dto.responses.ApiResponse;
import com.app.ridesync.dto.responses.RideRequestResponse;
import com.app.ridesync.entities.RequestStatus;
import com.app.ridesync.entities.Ride;
import com.app.ridesync.entities.RideRequestInfo;
import com.app.ridesync.entities.User;
import com.app.ridesync.projections.RideRequestProjection;
import com.app.ridesync.repositories.RideRepository;
import com.app.ridesync.repositories.RideRequestRepository;
import com.app.ridesync.services.AuthenticationService;
import com.app.ridesync.services.RideInfoService;
import com.app.ridesync.services.RideRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.mail.MessagingException;

@SpringBootTest
@AutoConfigureMockMvc
public class RideRequestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthenticationService userService;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private RideRequestRepository rideRequestRepository;

    @Autowired
    private RideRequestController rideRequestController;

    @Mock
    private RideRequestService mockRideRequestService;

    @Autowired
    private RideInfoService rideInfoService;

    private static User rider;
    private static User driver;
    private static String BEARER_TOKEN;
    private static Integer rideId;
    private static Integer rideRequestId;

    @BeforeEach
    private void setup() {
        setupUser();
    }

    @AfterEach()
    private void teardown() {
        clearUser(rider);
        clearUser(driver);
    }

    public void setupUser() {
        rider = createRider();
        driver = createDriver();
        verifyUser(rider);
        verifyUser(driver);
        authenticateUser();
        rideId = addRide();
        rideRequestId = addRideRequest();
    }

    private User createRider() {
        RegisterRequest request = new RegisterRequest("admin", "testRider@gmail.com", "abc", new Date(), "123456",
                "99999 99999");
        try {
            User user = userService.register(request).getUser();
            return user;
        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private User createDriver() {
        RegisterRequest request = new RegisterRequest("admin", "testDriver@gmail.com", "abc", new Date(), "123456",
                "99999 99999");
        try {
            User user = userService.register(request).getUser();
            return user;
        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        }

    }

    private void verifyUser(User user) {
        userService.verifyEmail(user.getUserId(), user.getEmail());
    }

    private void authenticateUser() {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder().email("testRider@gmail.com")
                .password("123456").build();
        BEARER_TOKEN = userService.authenticate(authenticationRequest).getToken();
    }

    private Integer addRide() {
        Ride ride = new Ride();
        ride.setStatus("posted");
        ride.setDescription("Ride from Sydney to Halifax");
        ride.setSeatsAvailable(3);
        ride.setVehicleId(1);
        ride.setUserId(driver.getUserId());
        rideRepository.save(ride);
        RideInfoInput rideInfoInput = new RideInfoInput();
        rideInfoInput.setUserId(driver.getUserId());
        rideInfoInput.setRideId(ride.getRideId());
        rideInfoInput.setFare(50);
        rideInfoService.addRideInfo(rideInfoInput);
        return ride.getRideId();

    }

    private Integer addRideRequest() {
        RideRequestInfo rideRequestInfo = RideRequestInfo.builder()
                .rideId(rideId)
                .driverId(2)
                .riderId(rider.getUserId())
                .requestStatus(RequestStatus.REQUESTED)
                .startLocationId(2)
                .endLocationId(2)
                .createdTime(LocalDateTime.now())
                .build();
        rideRequestRepository.save(rideRequestInfo);
        return rideRequestInfo.getRideRequestId();
    }

    private void clearUser(User user) {
        userService.deleteByUserId(user.getUserId());
    }

    @Test
    void testAddRide_Success() throws Exception {
        RideRequest rideRequest = RideRequest.builder()
                .lattitude1(1.0)
                .longitude1(2.0)
                .landmark1("Landmark 1")
                .address1("Address 1")
                .lattitude2(3.0)
                .longitude2(4.0)
                .landmark2("Landmark 2")
                .address2("Address 2")
                .rideId(rideId)
                .driverId(456)
                .estimatedTripStartTime(LocalDateTime.parse("2024-03-06T12:00:00"))
                .build();
        String requestBody = objectMapper.writeValueAsString(rideRequest);
        mockMvc.perform(post("/api/v1/request/addRequest")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + BEARER_TOKEN)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testAddRide_Failure() throws Exception {
        RideRequest rideRequest = new RideRequest();
        when(mockRideRequestService.requestRide("Bearer " + BEARER_TOKEN, rideRequest))
                .thenThrow(new RuntimeException());
        ReflectionTestUtils.setField(rideRequestController, "rideRequestService", mockRideRequestService);
        ResponseEntity<RideRequestResponse> response = rideRequestController.addRide("Bearer " + BEARER_TOKEN,
                rideRequest);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testGetRide() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(rideRequestController).build();
        mockMvc.perform(get("/api/v1/request/getRideRequest")
                .param("rideId", rideId.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetRide_Failure() throws Exception {
        when(mockRideRequestService.getRides(123)).thenThrow(new RuntimeException());
        ReflectionTestUtils.setField(rideRequestController, "rideRequestService", mockRideRequestService);
        ResponseEntity<ApiResponse<List<RideRequestProjection>>> response = rideRequestController.getRide(123);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testUpdateRideRequest_Success() throws Exception {

        RideRequest request = RideRequest.builder().requestStatus(RequestStatus.ACCEPTED).build();

        String requestBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/api/v1/request/updateRideRequest/{id}", rideRequestId)
                .header("Authorization", "Bearer " + BEARER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateRideRequest_Failure() throws Exception {
        when(mockRideRequestService.updateRide(BEARER_TOKEN, rideRequestId, null)).thenThrow(new RuntimeException());
        ReflectionTestUtils.setField(rideRequestController, "rideRequestService", mockRideRequestService);
        ResponseEntity<RideRequestResponse> response = rideRequestController.updateRideRequest(BEARER_TOKEN, rideId, null);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
