package com.app.ridesync.integrationtest;

import com.app.ridesync.controllers.AuthenticationController;
import com.app.ridesync.controllers.NotificationController;
import com.app.ridesync.dto.requests.AuthenticationRequest;
import com.app.ridesync.dto.requests.RegisterRequest;
import com.app.ridesync.dto.responses.ApiResponse;
import com.app.ridesync.entities.Notification;
import com.app.ridesync.entities.User;
import com.app.ridesync.repositories.NotificationRepository;
import com.app.ridesync.services.AuthenticationService;
import com.app.ridesync.services.JwtService;
import com.app.ridesync.services.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.mail.MessagingException;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationService userService;

    @Mock
    private NotificationService notificationService;

    @Autowired
    private NotificationController notificationController;

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
    void testGetNotifications() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/notification/getNotifications")
                        .header("Authorization", "Bearer "+BEARER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Result set was retrieved successfully"));
    }

    @Test
    void testGetNotifications_Failure() throws Exception {
       when(notificationService.getNotification(user.getUserId())).thenThrow(new RuntimeException());
       ReflectionTestUtils.setField(notificationController, "notificationService", notificationService);
        ResponseEntity<ApiResponse<List<Notification>>> response=notificationController.getNotifications("Bearer "+BEARER_TOKEN);
        assertEquals(response.getStatusCode(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
