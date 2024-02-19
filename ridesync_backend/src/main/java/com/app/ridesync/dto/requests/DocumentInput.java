package com.app.ridesync.dto.requests;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentInput {
	private String userDocumentID;
	private Integer userId;
	private String documentType;
	private LocalDate expiryDate;
	private Integer documentId;
}
