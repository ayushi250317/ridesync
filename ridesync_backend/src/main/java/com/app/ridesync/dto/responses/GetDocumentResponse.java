package com.app.ridesync.dto.responses;

import java.util.List;

import com.app.ridesync.entities.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetDocumentResponse {
	private List<Document> documents;
	private String message;
    private boolean success;
}
