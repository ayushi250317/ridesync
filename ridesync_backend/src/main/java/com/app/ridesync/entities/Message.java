package com.app.ridesync.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
	@Id
	@Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer messageId;
	private Integer senderId;
	private Integer recipientId;
	private String message;
	private LocalDateTime sentTime;
}
