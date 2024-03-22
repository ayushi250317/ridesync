package com.app.ridesync.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.app.ridesync.dto.requests.AuthenticationRequest;
import com.app.ridesync.dto.requests.PasswordResetRequest;
import com.app.ridesync.dto.requests.RegisterRequest;
import com.app.ridesync.dto.responses.AuthenticationResponse;
import com.app.ridesync.entities.User;
import com.app.ridesync.repositories.DocumentRepository;
import com.app.ridesync.repositories.UserRepository;
import com.app.ridesync.repositories.VehicleRepository;
import com.app.ridesync.services.AuthenticationService;
import com.app.ridesync.services.JwtService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = { AuthenticationService.class, AuthenticationManager.class })
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AuthenticationServiceTest {
    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationService authenticationService;

    @MockBean
    private DocumentRepository documentRepository;

    @MockBean
    private JavaMailSender javaMailSender;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private VehicleRepository vehicleRepository;

    @MockBean
    private MimeMessage mimeMessage;

    @Test
    public void testValidateRequest_UserExists() throws MessagingException {
        RegisterRequest request = RegisterRequest.builder()
                .email("testmail@gmail.com")
                .fullName("TestUser")
                .password("123456")
                .dateOfBirth(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .address("Test Address")
                .phoneNumber("7828828381")
                .build();
        User user = User.builder()
                .fullName("TestUser")
                .email("testmail@gmail.com")
                .password("123456")
                .dateOfBirth(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .address("Test Address")
                .phoneNumber("7828828381")
                .build();
        AuthenticationResponse expectedAuthenticationResponse = AuthenticationResponse.builder()
                .message("Email already registered")
                .success(false)
                .build();
        when(userRepository.findByEmail(request.getEmail())).thenReturn(user);
        AuthenticationResponse response = authenticationService.validateRequest(request);
        assertEquals(expectedAuthenticationResponse, response);
    }

    @Test
    public void testValidateRequest_UserDoesNotExist() throws MessagingException {
        RegisterRequest request = RegisterRequest.builder()
                .email("testmail@gmail.com")
                .fullName("TestUser")
                .password("123456")
                .dateOfBirth(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .address("Test Address")
                .phoneNumber("7828828381")
                .build();
        User user = User.builder()
                .fullName("TestUser")
                .email("testmail@gmail.com")
                .password("123456")
                .dateOfBirth(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .address("Test Address")
                .phoneNumber("7828828381")
                .build();
        when(userRepository.findByEmail(request.getEmail())).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(user);
        doNothing().when(javaMailSender).send(any(MimeMessage.class));
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        AuthenticationResponse response = authenticationService.validateRequest(request);
        assertEquals("Registration Successful", response.getMessage());
        assertTrue(response.isSuccess());
        verify(javaMailSender).send(mimeMessage);
    }

    @Test
    public void testAuthenticate_UserNotFound() {
        // Mock data
        String email = "test@example.com";
        String password = "password";
        when(userRepository.findByEmail(email)).thenReturn(null);

        AuthenticationRequest request = AuthenticationRequest.builder()
                .email(email)
                .password(password)
                .build();
        AuthenticationResponse expectedResponse = AuthenticationResponse.builder()
                .success(false)
                .message("Email not registered")
                .build();
        AuthenticationResponse response = authenticationService.authenticate(request);
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testAuthenticate_UserNotVerified() {
        String email = "test@example.com";
        String password = "password";
        User user = User.builder().email(email).password(password).isVerified(false).build();
        when(userRepository.findByEmail(email)).thenReturn(user);
        AuthenticationRequest request = AuthenticationRequest.builder()
                .email(email)
                .password(password)
                .build();
        AuthenticationResponse expectedResponse = AuthenticationResponse.builder()
                .message("Please verify your account")
                .success(false)
                .build();
        AuthenticationResponse response = authenticationService.authenticate(request);
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testAuthenticate_Success() {
        // Mock data
        String email = "test@example.com";
        String password = "password";
        User user = User.builder().email(email).password(password).isVerified(true).build();
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("token");
        when(documentRepository.findByUserId(user.getUserId())).thenReturn(null);
        when(vehicleRepository.findByUserId(user.getUserId())).thenReturn(null);
        AuthenticationResponse expectedResponse = AuthenticationResponse.builder()
                .user(user)
                .message("Login Successful")
                .success(true)
                .documents(null)
                .vehicles(null)
                .token("token")
                .build();

        AuthenticationRequest request = AuthenticationRequest.builder().email(email).password(password).build();
        AuthenticationResponse response = authenticationService.authenticate(request);
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testAuthenticate_Failure() {
        String email = "test@example.com";
        String password = "password";
        User user = User.builder().email(email).password(password).isVerified(true).build();
        when(userRepository.findByEmail(email)).thenReturn(user);
        doThrow(new AuthenticationException("Incorrect password") {
        }).when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        AuthenticationResponse expectedResponse = AuthenticationResponse.builder()
                .message("Incorrect Password")
                .success(false)
                .build();
        AuthenticationRequest request = AuthenticationRequest.builder().email(email).password(password).build();
        AuthenticationResponse response = authenticationService.authenticate(request);
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testVerifyEmail_Successful() {
        int userId = 1;
        String email = "test@example.com";
        User user = User.builder().userId(userId).email(email).isVerified(false).build();
        when(userRepository.findByUserId(userId)).thenReturn(user);
        AuthenticationResponse expectedResponse = AuthenticationResponse.builder()
                .message("Account Email Verification Successful")
                .success(true)
                .build();
        AuthenticationResponse response = authenticationService.verifyEmail(userId, email);
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testVerifyEmail_UnSuccessful() {
        int userId = 1;
        String email = "test@example.com";
        String userEmail = "test1@example.com";
        User user = User.builder().userId(userId).email(userEmail).isVerified(false).build();
        when(userRepository.findByUserId(userId)).thenReturn(user);
        AuthenticationResponse expectedResponse = AuthenticationResponse.builder()
                .message("Email Verification Unsuccessful")
                .success(false)
                .build();
        AuthenticationResponse response = authenticationService.verifyEmail(userId, email);
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testForgotPassword_Success() throws MessagingException {
        User user = User.builder().email("testuser@gmail.com").build();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        doNothing().when(javaMailSender).send(any(MimeMessage.class));
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email("testuser@gmail.com")
                .build();
        String resetToken = "mockedResetToken";
        when(jwtService.generateToken(user)).thenReturn(resetToken);
        AuthenticationResponse authenticationResponse = authenticationService.forgotPassword(authenticationRequest);
        assertEquals("email sent successfully", authenticationResponse.getMessage());
        assertTrue(authenticationResponse.isSuccess());
        verify(javaMailSender).send(any(MimeMessage.class));
    }

    @Test
    public void testForgotPassword_EmailDoesNotExist() throws MessagingException {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email("testuser@gmail.com")
                .build();
        when(userRepository.findByEmail(authenticationRequest.getEmail())).thenReturn(null);
        AuthenticationResponse authenticationResponse = authenticationService.forgotPassword(authenticationRequest);
        assertEquals("Email do not exist", authenticationResponse.getMessage());
        assertFalse(authenticationResponse.isSuccess());
    }

    @Test
    public void testUpdateUserDetails() {
        // Arrange
        User user = User.builder()
                .address("42 Main St")
                .dateOfBirth(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .fullName("Dr Jane Doe")
                .phoneNumber("6625550144")
                .build();
        User updateUser = User.builder()
                .address("423 Main St")
                .dateOfBirth(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .fullName("Dr Jane Doe")
                .phoneNumber("6625550144")
                .build();

        when(userRepository.save(Mockito.<User>any())).thenReturn(updateUser);
        when(userRepository.findByUserId(Mockito.<Integer>any())).thenReturn(user);
        User actualUpdateUserDetailsResult = authenticationService.updateUserDetails(new RegisterRequest(), 1);
        verify(userRepository).findByUserId(Mockito.<Integer>any());
        verify(userRepository).save(Mockito.<User>any());
        assertSame(updateUser, actualUpdateUserDetailsResult);
    }

    @Test
    public void testResetPassword_SuccessfulVerification() {
        User user = new User();
        user.setUserId(1);
        when(userRepository.findByUserId(1)).thenReturn(user);
        when(jwtService.extractUserId("valid_token")).thenReturn(1);
        AuthenticationResponse expectedResponse = AuthenticationResponse.builder()
                .message("Verification done successfully").success(true).build();
        AuthenticationResponse response = authenticationService.resetPassword(1, "valid_token");
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testResetPassword_UnsuccessfulVerification() {
        User user = new User();
        user.setUserId(1);
        when(userRepository.findByUserId(1)).thenReturn(user);
        when(jwtService.extractUserId("valid_token")).thenReturn(5);
        AuthenticationResponse expectedResponse = AuthenticationResponse.builder()
                .message("Email did not match").success(false).build();
        AuthenticationResponse response = authenticationService.resetPassword(1, "valid_token");
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testSetNewPassword_Success() {
        PasswordResetRequest request = new PasswordResetRequest();
        request.setId(1); 
        request.setNewPassword("newPassword");
        request.setReNewPassword("newPassword");
        User user = new User();
        user.setUserId(1);
        when(userRepository.findByUserId(1)).thenReturn(user);
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword");
        AuthenticationResponse expectedResponse=AuthenticationResponse.builder().message("Password Reset Successful").success(true).build();
        AuthenticationResponse response = authenticationService.setNewPassword(request);
        assertEquals(expectedResponse, response);
        verify(userRepository, times(1)).findByUserId(1);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testSetNewPassword_Failure() {
        PasswordResetRequest request = new PasswordResetRequest();
        request.setId(1); 
        request.setNewPassword("newPassword");
        request.setReNewPassword("newPassword1");
        User user = new User();
        user.setUserId(1);
        when(userRepository.findByUserId(1)).thenReturn(user);
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword");
        AuthenticationResponse expectedResponse=AuthenticationResponse.builder().message("Passwords do not match").success(false).build();
        AuthenticationResponse response = authenticationService.setNewPassword(request);
        assertEquals(expectedResponse, response);
    }
}
