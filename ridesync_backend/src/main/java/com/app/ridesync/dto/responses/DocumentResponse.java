package com.app.ridesync.dto.responses;

import com.app.ridesync.entities.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentResponse {
	private Document document;
	private String message;
    private boolean success;
}
