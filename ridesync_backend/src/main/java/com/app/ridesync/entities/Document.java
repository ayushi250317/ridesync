package com.app.ridesync.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "document")
public class Document{
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer documentId;
	private String userDocumentID;
	private Integer userId;
	private String documentType;
	private LocalDate expiryDate;
	
	public Document(String documentName, Integer userId, String documentType, LocalDate expiryDate) {
		super();
		this.userDocumentID = documentName;
		this.userId = userId;
		this.documentType = documentType;
		this.expiryDate = expiryDate;
	}

}
