package com.app.ridesync.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Message {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer messageId;
	private Integer senderId;
	private Integer recipientId;
	private String message;
	private LocalDateTime sentTime;
}
