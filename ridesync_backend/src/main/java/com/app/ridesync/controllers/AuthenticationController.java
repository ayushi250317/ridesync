package com.app.ridesync.controllers;

import com.app.ridesync.dto.requests.AuthenticationRequest;
import com.app.ridesync.dto.requests.PasswordResetRequest;
import com.app.ridesync.dto.requests.RegisterRequest;
import com.app.ridesync.dto.responses.AuthenticationResponse;
import com.app.ridesync.services.AuthenticationService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor

public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    @CrossOrigin(origins = "*")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) throws MessagingException{
        return ResponseEntity.ok(service.register(request));
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
}