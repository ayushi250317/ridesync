package com.app.ridesync.dto.responses;

import com.app.ridesync.entities.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DocumentResponse {
	private Document document;
	private String message;
    private boolean success;
}
