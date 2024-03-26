package com.app.ridesync.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ridesync.dto.requests.RideRequest;
import com.app.ridesync.dto.responses.RideRequestResponse;
import com.app.ridesync.entities.Location;
import com.app.ridesync.entities.Notification;
import com.app.ridesync.entities.NotificationType;
import com.app.ridesync.entities.RequestStatus;
import com.app.ridesync.entities.RideInfo;
import com.app.ridesync.entities.RideRequestInfo;
import com.app.ridesync.entities.User;
import com.app.ridesync.projections.RideRequestProjection;
import com.app.ridesync.repositories.RideInfoRepository;
import com.app.ridesync.repositories.RideRequestRepository;
import com.app.ridesync.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RideRequestService {
        @Autowired
        private final RideRequestRepository rideRequestRepository;
        @Autowired
        private final LocationService locationService;
        @Autowired
        private final JwtService jwtService;
        @Autowired
        private final RideInfoRepository rideInfoRepository;
        @Autowired
        private final UserRepository userRepository;
        @Autowired
        private final NotificationService notificationService;

        public RideRequestResponse requestRide(String jwtToken, RideRequest rideRequest) {
                jwtToken = jwtToken.substring(7);
                Integer riderId=jwtService.extractUserId(jwtToken);
                Location startLocation = new Location();
                startLocation.setAddress(rideRequest.getAddress1());
                startLocation.setLandmark(rideRequest.getLandmark1());
                startLocation.setLattitude(rideRequest.getLattitude1());
                startLocation.setLongitude(rideRequest.getLongitude1());
                startLocation=locationService.addLocation(startLocation);

                Location endLocation = new Location();
                endLocation.setAddress(rideRequest.getAddress2());
                endLocation.setLandmark(rideRequest.getLandmark2());
                endLocation.setLattitude(rideRequest.getLattitude2());
                endLocation.setLongitude(rideRequest.getLongitude2());
                endLocation=locationService.addLocation(endLocation);
                
                RideRequestInfo rideRequestInfo = RideRequestInfo.builder()
                .rideId(rideRequest.getRideId())
                .driverId(rideRequest.getDriverId())
                .riderId(riderId)
                .requestStatus(RequestStatus.REQUESTED)
                .startLocationId(startLocation.getLocationId())
                .endLocationId(endLocation.getLocationId())
                .createdTime(LocalDateTime.now())
                .tripStartTime(rideRequest.getEstimatedTripStartTime())
                .build();
                
                rideRequestRepository.save(rideRequestInfo);
                
                User user=userRepository.findByUserId(riderId);
                Notification notification=Notification.builder()
                                .userId(rideRequest.getDriverId())
                                .contentId(rideRequestInfo.getRideId())
                                .message(user.getFullName()+" requested a ride")
                                .notificationType(NotificationType.RIDE)
                                .build();
                
                notificationService.addNotification(notification);
                
                return RideRequestResponse.builder().message("Ride requested successfully")
                                .success(true).build();
        }

        public List<RideRequestProjection> getRides(Integer rideId) {
             return rideRequestRepository.findByRideId(rideId);  
        }

        public RideRequestResponse updateRide(String jwtToken, Integer requestId, RideRequest request) {
                String requestStatus=request.getRequestStatus().toString();
                RideRequestInfo rideRequestInfo = rideRequestRepository.findByRideRequestId(requestId);
                User driver=userRepository.findByUserId(rideRequestInfo.getDriverId());
                if(requestStatus.equals("ACCEPTED")){
                        rideRequestInfo.setRequestStatus(RequestStatus.ACCEPTED);
                        Integer rideId=rideRequestInfo.getRideId();
                        Integer driverId=rideRequestInfo.getDriverId();
                        RideInfo driverRideInfo=rideInfoRepository.findByRideIdAndUserId(rideId,driverId);
                        RideInfo rideInfo= RideInfo.builder()
                        .rideId(rideRequestInfo.getRideId())
                        .userId(rideRequestInfo.getRiderId())
                        .startLocationId(rideRequestInfo.getStartLocationId())
                        .endLocationId(rideRequestInfo.getEndLocationId())
                        .isDriver(false)
                        .estimatedTripStartTime(rideRequestInfo.getTripStartTime())
                        .fare(driverRideInfo.getFare())
                        .build(); 
                        rideInfoRepository.save(rideInfo);
                        Notification notification=Notification.builder()
                                .userId(rideRequestInfo.getRiderId())
                                .contentId(rideRequestInfo.getRideId())
                                .message(driver.getFullName()+" accepted your ride request")
                                .notificationType(NotificationType.RIDE)
                                .build();
                        notificationService.addNotification(notification);
                }
                else if(requestStatus.equals("REJECTED")){
                        rideRequestInfo.setRequestStatus(RequestStatus.REJECTED); 
                        Notification notification=Notification.builder()
                                .userId(rideRequestInfo.getRiderId())
                                .contentId(rideRequestInfo.getRideId())
                                .message(driver.getFullName()+" rejected your ride request")
                                .notificationType(NotificationType.RIDE)
                                .build();
                        notificationService.addNotification(notification);      
                } 
                rideRequestRepository.save(rideRequestInfo);
                return RideRequestResponse.builder().message("Request updated successfully").success(true).build();
        }

}
