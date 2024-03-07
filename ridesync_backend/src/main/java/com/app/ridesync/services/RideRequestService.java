package com.app.ridesync.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ridesync.dto.requests.RideRequest;
import com.app.ridesync.dto.responses.RideRequestResponse;
import com.app.ridesync.entities.RequestStatus;
import com.app.ridesync.entities.RideRequestInfo;
import com.app.ridesync.repositories.RideRequestRepository;

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

        public RideRequestResponse requestRide(String jwtToken, RideRequest rideRequest) {
                return RideRequestResponse.builder().message("Ride requested successfully")
                                .success(true).build();
        }

        public RideRequestResponse getRides(Integer rideId) {
                return RideRequestResponse.builder().message("Requests fetched successfully").success(true).build();
        }

        public RideRequestResponse updateRide(Integer requestId, RideRequest request) {
                return RideRequestResponse.builder().message("Request updated successfully").success(true).build();
        }

}
