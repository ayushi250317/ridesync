package com.app.ridesync.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer notificationId;
    private Integer userId;
    private LocalDateTime timeStamp;
    private String message;
    private NotificationType notificationType;
    private Integer contentId;
    private Integer readFlag;


    public Notification(LocalDateTime timestamp, Integer userId, String message, NotificationType notificationType, Integer contentId, Integer read) {
        super();
        this.timeStamp = timestamp;
        this.userId = userId;
        this.contentId = contentId;
        this.message = message;
        this.notificationType = notificationType;
        this.readFlag = read;
    }
}