package com.app.ridesync.entities;

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
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatIdentifier {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer chatIdenfierId;
	private Integer senderId;
	private Integer recipientId;
	private String chatIdentifier;
}
