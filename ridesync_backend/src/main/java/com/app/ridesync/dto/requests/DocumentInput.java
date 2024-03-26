package com.app.ridesync.dto.requests;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
