package com.app.ridesync.controllers;

import com.app.ridesync.dto.responses.ApiResponse;
import com.app.ridesync.entities.Notification;
import com.app.ridesync.projections.RideHistoryProjection;
import com.app.ridesync.services.JwtService;
import com.app.ridesync.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="*")
@RequestMapping("/api/v1/notification")
@RestController
@RequiredArgsConstructor
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/getNotifications")
    public ResponseEntity<ApiResponse<List<Notification>>> getNotifications(@RequestHeader("Authorization") String jwtToken){
        try {
            Integer userId = jwtService.extractUserId(jwtToken.substring(7));

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(notificationService.getNotification(userId), true, "Result set was retrieved successfully"));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, false, "ERROR: " + e.getMessage()));
        }
    }
}