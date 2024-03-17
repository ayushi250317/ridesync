package com.app.ridesync.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.app.ridesync.dto.requests.RideRequest;
import com.app.ridesync.dto.responses.RideRequestResponse;
import com.app.ridesync.entities.Location;
import com.app.ridesync.entities.Notification;
import com.app.ridesync.entities.NotificationType;
import com.app.ridesync.entities.RequestStatus;
import com.app.ridesync.entities.RideInfo;
import com.app.ridesync.entities.RideRequestInfo;
import com.app.ridesync.entities.User;
import com.app.ridesync.repositories.RideInfoRepository;
import com.app.ridesync.repositories.RideRequestRepository;
import com.app.ridesync.repositories.UserRepository;
import com.app.ridesync.services.JwtService;
import com.app.ridesync.services.LocationService;
import com.app.ridesync.services.NotificationService;
import com.app.ridesync.services.RideRequestService;

public class RideRequestTest {

        @Mock
        private LocationService locationService;

        @Mock
        private NotificationService notificationService;

        @Mock
        private RideRequestRepository rideRequestRepository;

        @Mock
        private RideInfoRepository rideInfoRepository;

        @Mock
        private UserRepository userRepository;

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
                User user = User.builder()
                                .fullName("Ayushi Malhotra")
                                .email("ayushimalhotra9799@gmail.com")
                                .password("123456")
                                .build();
                when(userRepository.findByUserId(1)).thenReturn(user);
                RideRequest rideRequest=RideRequest.builder()
                .lattitude1(1.0)
                .longitude1(2.0)
                .landmark1("Landmark 1")
                .address1("Address 1")
                .lattitude2(3.0)
                .longitude2(4.0)
                .landmark2("Landmark 2")
                .address2("Address 2")
                .rideId(123)
                .driverId(456)
                .estimatedTripStartTime(LocalDateTime.parse("2024-03-06T12:00:00"))
                .build();
        
                Notification notification = Notification.builder()
                                .contentId(1)
                                .message("Driver accepted your ride")
                                .notificationType(NotificationType.RIDE)
                                .build();
                doNothing().when(notificationService).addNotification(notification);

                String jwtToken = "Bearer:eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIn0.uXy8l0MeVGqx2pUKqDb3ZWGqcJyO-o2BMLk4x6zRhkY";
                when(jwtService.extractUserId(jwtToken.substring(7))).thenReturn(1);
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

        @Test
        public void testGetRequestByRideId() {
                List<RideRequestInfo> expectedRequests = new ArrayList<>();
                RideRequestInfo request1 = RideRequestInfo.builder()
                                .rideId(1)
                                .driverId(1)
                                .riderId(2)
                                .requestStatus(RequestStatus.REQUESTED)
                                .startLocationId(1)
                                .endLocationId(2)
                                .tripStartTime(LocalDateTime.parse("2024-03-06T12:00:00"))
                                .build();
                RideRequestInfo request2 = RideRequestInfo.builder()
                                .rideId(1)
                                .driverId(1)
                                .riderId(3)
                                .requestStatus(RequestStatus.REQUESTED)
                                .startLocationId(3)
                                .endLocationId(4)
                                .tripStartTime(LocalDateTime.parse("2024-03-06T12:30:00"))
                                .build();
                expectedRequests.add(request1);
                expectedRequests.add(request2);
                Integer rideId = 1;
                when(rideRequestRepository.findByRideId(rideId)).thenReturn(expectedRequests);
                RideRequestResponse rideRequestResponse = rideRequestService.getRides(rideId);
                assertEquals(rideRequestResponse.getRequests(), expectedRequests);
                assertEquals("Requests fetched successfully", rideRequestResponse.getMessage());
        }

        @Test
        public void testUpdateRequest_Accepted() {
                RideRequest rideRequest = new RideRequest();
                User user = User.builder()
                                .userId(1)
                                .fullName("Ayushi Malhotra")
                                .email("ayushimalhotra9799@gmail.com")
                                .password("123456")
                                .build();
                Integer userId = 1;
                when(userRepository.findByUserId(userId)).thenReturn(user);
                rideRequest.setRequestStatus(RequestStatus.ACCEPTED);
                RideRequestInfo requestInfo = RideRequestInfo.builder()
                                .rideId(1)
                                .driverId(1)
                                .riderId(2)
                                .requestStatus(RequestStatus.REQUESTED)
                                .startLocationId(1)
                                .endLocationId(2)
                                .tripStartTime(LocalDateTime.parse("2024-03-06T12:00:00"))
                                .build();
                RideInfo rideInfo = RideInfo.builder()
                                .rideId(1)
                                .userId(1)
                                .isActive(false)
                                .startLocationId(1)
                                .endLocationId(1)
                                .fare(50)
                                .build();
                Integer requestId = 1;
                String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIn0.uXy8l0MeVGqx2pUKqDb3ZWGqcJyO-o2BMLk4x6zRhkY";

                Notification notification = Notification.builder()
                                .contentId(1)
                                .message("Driver accepted your ride")
                                .notificationType(NotificationType.RIDE)
                                .build();
                when(rideRequestRepository.findByRideRequestId(requestId)).thenReturn(requestInfo);
                when(rideInfoRepository.findByRideIdAndUserId(1, 1)).thenReturn(rideInfo);
                doNothing().when(notificationService).addNotification(notification);
                RideRequestResponse rideRequestResponse = rideRequestService.updateRide(jwtToken, requestId,
                                rideRequest);
                assertTrue(rideRequestResponse.isSuccess());
                assertEquals("Request updated successfully", rideRequestResponse.getMessage());
                assertEquals(RequestStatus.ACCEPTED, requestInfo.getRequestStatus());
                verify(rideRequestRepository).save(requestInfo);
        }

        @Test
        public void testUpdateRequest_Rejected() {
                User user = User.builder()
                                .userId(1)
                                .fullName("Ayushi Malhotra")
                                .email("ayushimalhotra9799@gmail.com")
                                .password("123456")
                                .build();
                Integer userId = 1;
                when(userRepository.findByUserId(userId)).thenReturn(user);
                Notification notification = Notification.builder()
                                .contentId(1)
                                .message("Driver rejected your ride")
                                .notificationType(NotificationType.RIDE)
                                .build();
                doNothing().when(notificationService).addNotification(notification);
                RideRequest rideRequest = new RideRequest();
                rideRequest.setRequestStatus(RequestStatus.REJECTED);
                RideRequestInfo requestInfo = RideRequestInfo.builder()
                                .rideId(1)
                                .driverId(1)
                                .riderId(2)
                                .requestStatus(RequestStatus.REQUESTED)
                                .startLocationId(1)
                                .endLocationId(2)
                                .tripStartTime(LocalDateTime.parse("2024-03-06T12:00:00"))
                                .build();
                Integer requestId = 1;
                String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIn0.uXy8l0MeVGqx2pUKqDb3ZWGqcJyO-o2BMLk4x6zRhkY";
                when(rideRequestRepository.findByRideRequestId(requestId)).thenReturn(requestInfo);
                RideRequestResponse rideRequestResponse = rideRequestService.updateRide(jwtToken, requestId,
                                rideRequest);
                assertTrue(rideRequestResponse.isSuccess());
                assertEquals("Request updated successfully", rideRequestResponse.getMessage());
                assertEquals(RequestStatus.REJECTED, requestInfo.getRequestStatus());
                verify(rideRequestRepository).save(requestInfo);
        }
}
