package com.app.ridesync.integrationtest;

import com.app.ridesync.controllers.NotificationController;
import com.app.ridesync.repositories.NotificationRepository;
import com.app.ridesync.services.JwtService;
import com.app.ridesync.services.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {NotificationController.class, JwtService.class, NotificationService.class})
@ExtendWith(SpringExtension.class)
class NotificationControllerTest {
    @Autowired
    private NotificationController notificationController;

    @MockBean
    private NotificationRepository notificationRepository;

    @Test
    void testGetNotifications() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/notification/getNotifications")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(notificationController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"responseObject\":null,\"success\":false,\"message\":\"ERROR: JWT strings must contain exactly 2 period"
                                        + " characters. Found: 0\"}"));
    }

}
