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
import jakarta.transaction.Transactional;

import com.app.ridesync.entities.Document;
import com.app.ridesync.entities.User;
import com.app.ridesync.entities.Vehicle;
import org.springframework.core.env.Environment;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;

/**
 * Service class handling user authentication-related operations.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private Environment env;
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

    @Value("${IP_ADDRESS}")
    private String ip_address;

    @Value("${FRONTEND_PORT}")
    private String port;

    /**
     * Validates the registration request and proceeds with registration if the
     * email is not already registered.
     * Sends a verification email upon successful registration.
     */
    public AuthenticationResponse validateRequest(RegisterRequest request) throws MessagingException {
        User user = repository.findByEmail(request.getEmail());
        if (user != null) {
            return AuthenticationResponse.builder().message("Email already registered").build();
        }
        return register(request);
    }

    /**
     * Registers a new user.
     * Saves the user details and sends a verification email.
     */
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
        message.setRecipients(MimeMessage.RecipientType.TO, request.getEmail());
        message.setSubject("Verify Ridesync Account");
        // String htmlContent="<p>Click the <a
        // href=\"http://172.17.1.101:3000/confirm_registration/"+user.getUserId()+"/"+user.getEmail()+"\">link</a>
        // to verify your email </p>";
        String htmlContent = "<p>Click the <a href=\"http://" + ip_address + ":" + port + "/confirm_registration/"
                + user.getUserId() + "/" + user.getEmail() + "\">link</a> to verify your email </p>";
        message.setContent(htmlContent, "text/html;charset=utf-8");
        javaMailSender.send(message);
        return AuthenticationResponse.builder()
                .message("Registration Successful")
                .success(true)
                .user(user)
                .build();

    }

    /**
     * Verifies a user's email address.
     * Marks the user as verified in the database.
     */
    public AuthenticationResponse verifyEmail(Integer id, String email) {
        User user = repository.findByUserId(id);
        if (user.getEmail().equals(email)) {
            user.setVerified(true);
            repository.save(user);
            return AuthenticationResponse.builder().message("Account Email Verification Successful").success(true)
                    .build();
        }
        return AuthenticationResponse.builder().message("Email Verification Unsuccessful").build();
    }

    /**
     * Authenticates a user.
     * Verifies if the user exists, is verified, and the password is correct.
     * Generates a JWT token upon successful authentication.
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = repository.findByEmail(request.getEmail());
        if (user == null) {
            return AuthenticationResponse.builder().message("Email not registered").build();
        }
        if (!user.isVerified()) {
            return AuthenticationResponse.builder().message("Please verify your account").build();
        }
        try {
            manager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (AuthenticationException e) {
            return AuthenticationResponse.builder().message("Incorrect Password").build();
        }
        List<Document> documents = documentRepository.findByUserId(user.getUserId());
        List<Vehicle> vehicles = vehicleRepository.findByUserId(user.getUserId());
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

    /**
     * Initiates the password reset process.
     * Sends an email with a password reset link.
     */
    public AuthenticationResponse forgotPassword(AuthenticationRequest request) throws MessagingException {
        String ip_address = env.getProperty("IP_ADDRESS");
        String port = env.getProperty("FRONTEND_PORT");
        User user = repository.findByEmail(request.getEmail());
        if (user == null) {
            return AuthenticationResponse.builder().message("Email do not exist").build();
        }
        String resetToken = jwtService.generateToken(user);
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom("ayushimalhotra9799@gmail.com");
        message.setRecipients(MimeMessage.RecipientType.TO, request.getEmail());
        message.setSubject("Reset Password");
        // String htmlContent="<p>Click the <a
        // href=\"http://172.17.1.101:3000/confirm_password/"+resetToken+"/"+user.getUserId()+"\">link</a>
        // to reset your password </p>";
        String htmlContent = "<p>Click the <a href=\"http://" + ip_address + ":" + port + "/confirm_password/"
                + resetToken + "/" + user.getUserId() + "\">link</a> to reset your password </p>";
        message.setContent(htmlContent, "text/html;charset=utf-8");
        javaMailSender.send(message);
        return AuthenticationResponse.builder().message("email sent successfully").success(true).build();

    }

    /**
     * Verifies the password reset token and user ID.
     */
    public AuthenticationResponse resetPassword(Integer id, String token) {
        User user = repository.findByUserId(id);
        Integer tokenId = jwtService.extractUserId(token);
        if (user.getUserId().equals(tokenId)) {
            return AuthenticationResponse.builder().message("Verification done successfully").success(true).build();
        }
        return AuthenticationResponse.builder().message("Email did not match").build();
    }

    /**
     * Sets a new password for the user after successful verification.
     */
    public AuthenticationResponse setNewPassword(PasswordResetRequest request) {
        if (request.getNewPassword().equals(request.getReNewPassword())) {
            User user = repository.findByUserId(request.getId());
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            repository.save(user);
            return AuthenticationResponse.builder().message("Password Reset Successful").success(true).build();
        }
        return AuthenticationResponse.builder().message("Passwords do not match").build();
    }

    /**
     * Updates the user's details.
     */
    public User updateUserDetails(RegisterRequest request, Integer userId) {
        User user = repository.findByUserId(userId);
        user.setFullName(request.getFullName());
        user.setAddress(request.getAddress());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setPhoneNumber(request.getPhoneNumber());

        return repository.save(user);
    }

    /**
     * Deletes a user by their ID.
     */
    @Transactional
    public void deleteByUserId(Integer userId) {
        repository.deleteByUserId(userId);
    }
}
