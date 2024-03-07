package com.app.ridesync.services;

import com.app.ridesync.dto.requests.AuthenticationRequest;
import com.app.ridesync.dto.requests.PasswordResetRequest;
import com.app.ridesync.dto.requests.RegisterRequest;
import com.app.ridesync.dto.responses.AuthenticationResponse;
import com.app.ridesync.repositories.DocumentRepository;
import com.app.ridesync.repositories.UserRepository;
import com.app.ridesync.repositories.VehicleRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import com.app.ridesync.entities.Document;
import com.app.ridesync.entities.User;
import com.app.ridesync.entities.Vehicle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	@Autowired
    private final UserRepository repository;
    @Autowired
    private final DocumentRepository documentRepository;
    @Autowired
    private final VehicleRepository vehicleRepository;
	@Autowired
    private final PasswordEncoder passwordEncoder;
	@Autowired
    private final JwtService jwtService;
	@Autowired
    private final AuthenticationManager manager;
    @Autowired
    private final JavaMailSender javaMailSender;

    public AuthenticationResponse validateRequest(RegisterRequest request) throws MessagingException {
        User user=repository.findByEmail(request.getEmail());
        if(user!=null){
            return AuthenticationResponse.builder().message("Email already registered").build();
        }
        return register(request);
    }

    public AuthenticationResponse register(RegisterRequest request) throws MessagingException {
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .address(request.getAddress())
                .dateOfBirth(request.getDateOfBirth())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        repository.save(user);
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom("ayushimalhotra9799@gmail.com");
        message.setRecipients(MimeMessage.RecipientType.TO,request.getEmail());
        message.setSubject("Verify Ridesync Account");
        String htmlContent="<p>Click the <a href=\"http://172.17.1.101:3000/confirm_registration/"+user.getUserId()+"/"+user.getEmail()+"\">link</a> to verify your email </p>";
        message.setContent(htmlContent,"text/html;charset=utf-8");
        javaMailSender.send(message);
        return AuthenticationResponse.builder()
                .message("Registration Successful")
                .success(true)
                .user(user)
                .build();
    
    }
    public AuthenticationResponse verifyEmail(Integer id,String email) {
        User user=repository.findByUserId(id);
        if(user.getEmail().equals(email)){
            user.setVerified(true);
            repository.save(user);
            return AuthenticationResponse.builder().message("Account Email Verification Successful").success(true).build();
        }
       return AuthenticationResponse.builder().message("Email Verification Unsuccessful").build(); 
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user=repository.findByEmail(request.getEmail());
        if(user==null){
            return AuthenticationResponse.builder().message("Email not registered").build();
        }
        if(!user.isVerified()){
           return AuthenticationResponse.builder().message("Please verify your account").build();
        }
        try{
        manager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        }
        catch(AuthenticationException e){
            return AuthenticationResponse.builder().message("Incorrect Password").build();
        }
        List<Document> documents=documentRepository.findByUserId(user.getUserId());
        List<Vehicle> vehicles=vehicleRepository.findByUserId(user.getUserId());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .message("Login Successful")
                .success(true)
                .user(user)
                .token(jwtToken)
                .documents(documents)
                .vehicles(vehicles)
                .build();
    }

    public AuthenticationResponse forgotPassword(AuthenticationRequest request) throws MessagingException {
      User user=repository.findByEmail(request.getEmail());
      if(user==null){
        return AuthenticationResponse.builder().message("Email do not exist").build();
      }
      String resetToken=jwtService.generateToken(user);
      MimeMessage message = javaMailSender.createMimeMessage();
      message.setFrom("ayushimalhotra9799@gmail.com");
      message.setRecipients(MimeMessage.RecipientType.TO,request.getEmail());
      message.setSubject("Reset Password");
      String htmlContent="<p>Click the <a href=\"http://172.17.1.101:3000/confirm_password/"+resetToken+"/"+user.getUserId()+"\">link</a> to reset your password </p>";
      message.setContent(htmlContent,"text/html;charset=utf-8");
      javaMailSender.send(message);
      return AuthenticationResponse.builder().message("email sent successfully").success(true).build();

}

    public AuthenticationResponse resetPassword(Integer id, String token) {
        User user=repository.findByUserId(id);
        Integer tokenId=jwtService.extractUserId(token);
        if(user.getUserId().equals(tokenId)){
            return AuthenticationResponse.builder().message("Verification done successfully").success(true).build();    
        }
        return AuthenticationResponse.builder().message("Email did not match").build();
    }

    public AuthenticationResponse setNewPassword(PasswordResetRequest request) {
        if(request.getNewPassword().equals(request.getReNewPassword())){
            User user=repository.findByUserId(request.getId());
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            repository.save(user);
            return AuthenticationResponse.builder().message("Password Reset Successful").success(true).build();
        }
        return AuthenticationResponse.builder().message("Passwords do not match").build();
    }

    public User updateUserDetails(RegisterRequest request, Integer userId){
        User old = repository.findByUserId(userId);
        old.setFullName(request.getFullName());
        old.setAddress(request.getAddress());
        old.setDateOfBirth(request.getDateOfBirth());
        old.setPhoneNumber(request.getPhoneNumber());

        return repository.save(old);
    }

}
