package com.app.ridesync.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleInput {
	private String regNo;
	private Integer documentId; //Insurance Document. must link to the document table.
	private String model;
	private String make;
	private String type;
	private Integer userId;
	private Integer vehicleId;

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
