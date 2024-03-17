package com.app.ridesync.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.app.ridesync.entities.Notification;
import com.app.ridesync.entities.NotificationType;
import com.app.ridesync.repositories.NotificationRepository;
import com.app.ridesync.services.NotificationService;

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
        notification.setMessage("Test notification");
        notification.setNotificationType(NotificationType.RIDE);
        notification.setNotificationId(1);
        notification.setReadFlag(1);
        notification.setTimeStamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        notification.setUserId(1);
        when(notificationRepository.save(Mockito.<Notification>any())).thenReturn(notification);
        // Act
        notificationService.addNotification(notification);
        // Assert
        verify(notificationRepository).save(Mockito.<Notification>any());
        assertEquals(0, notification.getReadFlag().intValue());
    }


    @Test
    void testGetNotification() {
        // Arrange
        Notification notification=Notification.builder()
        .contentId(1)
        .userId(1)
        .message("Test notification")
        .notificationType(NotificationType.RIDE)
        .build();
        ArrayList<Notification> notificationList = new ArrayList<>();
        notificationList.add(notification);
        when(notificationRepository.findByUserId(1))
                .thenReturn(notificationList);
        // Act
        List<Notification> actualNotification = notificationService.getNotification(1);
        // Assert
        verify(notificationRepository).findByUserId(Mockito.<Integer>any());
        assertSame(notificationList, actualNotification);
    }


}
