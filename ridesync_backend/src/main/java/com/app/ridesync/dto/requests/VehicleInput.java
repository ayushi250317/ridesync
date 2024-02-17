package com.app.ridesync.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleInput {
	private String regNo;
	private long documentId; //Insurance Document. must link to the document table.
	private String model;
	private String make;
	private String type;
	private String userId;
}
