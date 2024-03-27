package com.app.ridesync.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import com.app.ridesync.controllers.AuthenticationController;
import com.app.ridesync.dto.requests.AuthenticationRequest;
import com.app.ridesync.dto.requests.PasswordResetRequest;
import com.app.ridesync.dto.requests.RegisterRequest;
import com.app.ridesync.dto.responses.ApiResponse;
import com.app.ridesync.dto.responses.AuthenticationResponse;
import com.app.ridesync.entities.User;
import com.app.ridesync.services.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.mail.MessagingException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthenticationService userService;

    @Autowired
    private AuthenticationController authenticationController;

    private static User user;
    private static String BEARER_TOKEN;

    @BeforeEach
    private void setup() {
        setupUser();
    }

    @AfterEach()
    private void teardown() {
        clearUser();
    }

    public void setupUser() {
        createUser();
        verifyUser();
        authenticateUser();
    }

    private void createUser() {
        RegisterRequest request = new RegisterRequest("admin", "testmail@gmail.com", "abc", new Date(), "123456",
                "99999 99999");
        try {
            user = userService.register(request).getUser();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void verifyUser() {
        userService.verifyEmail(user.getUserId(), user.getEmail());
    }

    private void authenticateUser() {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder().email("testmail@gmail.com")
                .password("123456").build();
        BEARER_TOKEN = userService.authenticate(authenticationRequest).getToken();
    }

    private void clearUser() {
        userService.deleteByUserId(user.getUserId());
    }

    @Test
    public void testRegisterEndpoint_Success() throws Exception {
        RegisterRequest request = RegisterRequest.builder()
                .email("test1mail@gmail.com")
                .fullName("TestUser")
                .password("123456")
                .dateOfBirth(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .address("Test Address")
                .phoneNumber("7828828381")
                .build();

        String requestBody = objectMapper.writeValueAsString(request);
        ResultActions resultActions = mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Registration Successful"));
    }

    @Test
    public void testRegisterEndpoint_Failure() throws Exception {
        RegisterRequest request = RegisterRequest.builder()
                .fullName("TestUser")
                .password("123456")
                .dateOfBirth(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .address("Test Address")
                .phoneNumber("7828828381")
                .build();

        String requestBody = objectMapper.writeValueAsString(request);
        ResultActions resultActions = mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
        resultActions.andExpect(status().isInternalServerError());
        AuthenticationResponse authenticationResponse = objectMapper.readValue(
                resultActions.andReturn().getResponse().getContentAsString(),
                AuthenticationResponse.class);
        assertFalse(authenticationResponse.isSuccess());
    }

    @Test
    public void testAuthenticate_Success() throws Exception{
        AuthenticationRequest request= AuthenticationRequest.builder()
                                 .email("testmail@gmail.com")
                                .password("123456")
                                .build();
        String requestBody = objectMapper.writeValueAsString(request);
        ResultActions resultActions = mockMvc.perform(post("/api/v1/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    public void testAuthenticate_Failure() throws Exception{
        AuthenticationService spyAuthenticationService=Mockito.mock(AuthenticationService.class);
        AuthenticationRequest request= AuthenticationRequest.builder()
                                 .email("testmail@gmail.com")
                                .password("123456")
                                .build();
        when(spyAuthenticationService.authenticate(request)).thenThrow(new RuntimeException());
        ReflectionTestUtils.setField(authenticationController, "service", spyAuthenticationService);
        ResponseEntity<AuthenticationResponse> response=authenticationController.authenticate(request);
       assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testForgotPassword_Success() throws Exception {
        AuthenticationRequest request = AuthenticationRequest.builder()
                                        .email("testmail@gmail.com")   
                                        .build();
        String requestBody = objectMapper.writeValueAsString(request);
        ResultActions resultActions=mockMvc.perform(post("/api/v1/auth/forgotPassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    public void testForgotPassword_Failure() throws Exception {
        AuthenticationService spyAuthenticationService=Mockito.mock(AuthenticationService.class);
        AuthenticationRequest request = AuthenticationRequest.builder()
                                        .email("testmail@gmail.com")   
                                        .build();
        String requestBody = objectMapper.writeValueAsString(request);
        when(spyAuthenticationService.forgotPassword(request)).thenThrow(new MessagingException("Failed to send email"));
        ReflectionTestUtils.setField(authenticationController, "service", spyAuthenticationService);
        ResponseEntity<AuthenticationResponse> response=authenticationController.forgotPassword(request);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testResetPassword_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/auth/resetPassword")
                .param("token", BEARER_TOKEN)
                .param("id",user.getUserId().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testResetPassword_Failure() throws Exception{
        AuthenticationService spyAuthenticationService=Mockito.mock(AuthenticationService.class);
        when(spyAuthenticationService.resetPassword(user.getUserId(),BEARER_TOKEN)).thenThrow(new RuntimeException());
        ReflectionTestUtils.setField(authenticationController, "service", spyAuthenticationService);
        ResponseEntity<AuthenticationResponse> response=authenticationController.resetPassword(BEARER_TOKEN, user.getUserId());
       assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testVerifyEmail() throws Exception {
        mockMvc.perform(get("/api/v1/auth/verifyEmail")
                .param("email", "testmail@gmail.com")
                .param("id", user.getUserId().toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void testVerifyEmail_Failure() throws Exception{
        String email="testmail@gmail.com";
        AuthenticationService spyAuthenticationService=Mockito.mock(AuthenticationService.class);
        when(spyAuthenticationService.verifyEmail(user.getUserId(),email)).thenThrow(new RuntimeException());
        ReflectionTestUtils.setField(authenticationController, "service", spyAuthenticationService);
        ResponseEntity<AuthenticationResponse> response=authenticationController.verifyEmail(email, user.getUserId());
       assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testSetNewPassword() throws Exception {
        PasswordResetRequest request = new PasswordResetRequest();
        request.setId(user.getUserId());
        request.setNewPassword("newPassword");
        request.setReNewPassword("newPassword");
        String requestBody=objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/api/v1/auth/newPassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Password Reset Successful"));
    }

    @Test
    public void testSetNewPassword_Failure() throws Exception{
        PasswordResetRequest request = new PasswordResetRequest();
        request.setId(user.getUserId());
        request.setNewPassword("newPassword");
        request.setReNewPassword("newPassword");
        AuthenticationService spyAuthenticationService=Mockito.mock(AuthenticationService.class);
        when(spyAuthenticationService.setNewPassword(request)).thenThrow(new RuntimeException());
        ReflectionTestUtils.setField(authenticationController, "service", spyAuthenticationService);
        ResponseEntity<AuthenticationResponse> response=authenticationController.setNewPassword(request);
       assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    } 

    @Test
    public void testUpdateUserDetails() throws Exception {
        RegisterRequest request = RegisterRequest.builder()
                                .fullName("TestUser")
                                .build();
        String requestBody=objectMapper.writeValueAsString(request);
        mockMvc.perform(put("/api/v1/auth/updateUser")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+BEARER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Result set was retrieved successfully"));
    }

    @Test
    public void testUpdateUserDetails_Failure() throws Exception{
        RegisterRequest request = RegisterRequest.builder()
                                .fullName("TestUser")
                                .build();
        AuthenticationService spyAuthenticationService=Mockito.mock(AuthenticationService.class);
        when(spyAuthenticationService.updateUserDetails(request,user.getUserId())).thenThrow(new RuntimeException());
        ReflectionTestUtils.setField(authenticationController, "service", spyAuthenticationService);
        ResponseEntity<ApiResponse<User>> response=authenticationController.updateUserDetails("Bearer: "+BEARER_TOKEN, request);
       assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    } 
}


