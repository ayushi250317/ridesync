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
    private String messageType;
    private Integer id;
    private Integer readFlag;


    public Notification(LocalDateTime timestamp, Integer userId, String message, String messageType, Integer id, Integer read) {
        super();
        this.timeStamp = timestamp;
        this.userId = userId;
        this.id = id;
        this.message = message;
        this.messageType = messageType;
        this.readFlag = read;
    }
}