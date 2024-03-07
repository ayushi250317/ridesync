package com.app.ridesync.controllers;

import com.app.ridesync.dto.requests.AuthenticationRequest;
import com.app.ridesync.dto.requests.PasswordResetRequest;
import com.app.ridesync.dto.requests.RegisterRequest;
import com.app.ridesync.dto.responses.ApiResponse;
import com.app.ridesync.dto.responses.AuthenticationResponse;
import com.app.ridesync.entities.User;
import com.app.ridesync.projections.RideHistoryProjection;
import com.app.ridesync.services.AuthenticationService;

import com.app.ridesync.services.JwtService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor

public class AuthenticationController {
    private final AuthenticationService service;

    @Autowired
    JwtService jwtService;

    @PostMapping("/register")
    @CrossOrigin(origins = "*")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) throws MessagingException{
        return ResponseEntity.ok(service.validateRequest(request));
    }

    @PostMapping("/authenticate")
    @CrossOrigin(origins = "*")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/forgotPassword")
    @CrossOrigin(origins = "*")
    public ResponseEntity<AuthenticationResponse> forgotPassword(
            @RequestBody AuthenticationRequest request
    ) throws MessagingException {
        return ResponseEntity.ok(service.forgotPassword(request));
    }

    @GetMapping("/resetPassword")
    @CrossOrigin(origins = "*")
    public ResponseEntity<AuthenticationResponse> resetPassword
    (@RequestParam String token,
     @RequestParam Integer id
    ){
        return ResponseEntity.ok(service.resetPassword(id,token));
}

    @GetMapping("/verifyEmail")
    @CrossOrigin(origins = "*")
    public ResponseEntity<AuthenticationResponse> verifyEmail
    (@RequestParam String email,
     @RequestParam Integer id
    ){
        return ResponseEntity.ok(service.verifyEmail(id,email));  
    }

    @PostMapping("/newPassword")
    @CrossOrigin(origins = "*")
    public ResponseEntity<AuthenticationResponse> setNewPassword(@RequestBody PasswordResetRequest request)
   {    
    return ResponseEntity.ok(service.setNewPassword(request));
    }

    @PostMapping("/updateUser")
    @CrossOrigin(origins = "*")
    public ResponseEntity<ApiResponse<User>> updateUserDetails(@RequestHeader("Authorization") String jwtToken, @RequestBody RegisterRequest request){
        Integer userId = jwtService.extractUserId(jwtToken.substring(7));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(service.updateUserDetails(request, userId), true, "Result set was retrieved successfully"));
    }
}
