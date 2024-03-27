package com.app.ridesync.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ridesync.entities.Message;
import com.app.ridesync.entities.Notification;
import com.app.ridesync.entities.NotificationType;
import com.app.ridesync.entities.User;
import com.app.ridesync.repositories.NotificationRepository;
import com.app.ridesync.repositories.UserRepository;

/**
 * Service class responsible for managing notifications.
 */
@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Adds a notification to the database.
     * Sets the read flag to 0 (unread) and timestamp to the current date and time.
     */
    public void addNotification(Notification notification) {
        notification.setReadFlag(0);
        notification.setTimeStamp(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    /**
     * Adds a notification to the database.
     * Sets the read flag to 0 (unread) and timestamp to the current date and time.
     */
    public List<Notification> getNotification(Integer userId) {
        List<Notification> result = notificationRepository.findByUserIdOrderByTimeStampDesc(userId);
        for (Notification n : result) {
            n.setReadFlag(1);
            notificationRepository.save(n);
        }
        return result;
    }

    /**
     * Retrieves notifications for a given user and marks them as read.
     */
    public Notification createNotificationFromMessage(Message message) {

        return Notification.builder()
                .readFlag(0)
                .timeStamp(LocalDateTime.now())
                .userId(message.getSenderId())
                .contentId(message.getMessageId())
                .message(constructNotificationMessage(message))
                .notificationType(NotificationType.CHAT)
                .build();
    }

    /**
     * Creates a notification from a message.
     */
    private String constructNotificationMessage(Message message) {
        User user = userRepository.findByUserId(message.getSenderId());

        return String.format(" %s is trying to reach you.", user.getFullName());
    }
}
