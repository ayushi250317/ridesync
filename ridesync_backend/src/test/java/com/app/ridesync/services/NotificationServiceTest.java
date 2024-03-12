package com.app.ridesync.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.app.ridesync.entities.Notification;
import com.app.ridesync.entities.NotificationType;
import com.app.ridesync.repositories.NotificationRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {NotificationService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class NotificationServiceTest {
    @MockBean
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationService notificationService;


    @Test
    void testAddNotification() {
        // Arrange
        Notification notification = new Notification();
        notification.setContentId(1);
        notification.setMessage("Not all who wander are lost");
        notification.setNotificationType(NotificationType.MESSAGE);
        notification.setNotificationId(1);
        notification.setReadFlag(1);
        notification.setTimeStamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        notification.setUserId(1);
        when(notificationRepository.save(Mockito.<Notification>any())).thenReturn(notification);

        Notification notification2 = new Notification();
        notification2.setContentId(1);
        notification2.setMessage("Not all who wander are lost");
        notification2.setNotificationType(NotificationType.REQUEST);
        notification2.setNotificationId(1);
        notification2.setReadFlag(1);
        notification2.setTimeStamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        notification2.setUserId(1);

        // Act
        boolean actualAddNotificationResult = notificationService.addNotification(notification2);

        // Assert
        verify(notificationRepository).save(Mockito.<Notification>any());
        assertEquals(0, notification2.getReadFlag().intValue());
        assertFalse(actualAddNotificationResult);
    }


    @Test
    void testGetNotification() {
        // Arrange
        ArrayList<Notification> notificationList = new ArrayList<>();
        when(notificationRepository.findByUserIdAndReadFlag(Mockito.<Integer>any(), Mockito.<Integer>any()))
                .thenReturn(notificationList);

        // Act
        List<Notification> actualNotification = notificationService.getNotification(1);

        // Assert
        verify(notificationRepository).findByUserIdAndReadFlag(Mockito.<Integer>any(), Mockito.<Integer>any());
        assertTrue(actualNotification.isEmpty());
        assertSame(notificationList, actualNotification);
    }


}
