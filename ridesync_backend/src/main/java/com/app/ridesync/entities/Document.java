package com.app.ridesync.entities;

import java.time.LocalDate;
import java.util.Date;

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
	private long documentId;
	private String documentName;
	private String userId;
	private String documentType;
	private LocalDate expiryDate;
	
	public Document(String documentName, String userId, String documentType, LocalDate expiryDate) {
		super();
		this.documentName = documentName;
		this.userId = userId;
		this.documentType = documentType;
		this.expiryDate = expiryDate;
	}
	
	public long getDocumentId() {
		return documentId;
	}
	public void setDocumentId(long documentId) {
		this.documentId = documentId;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public LocalDate getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	
	
}
