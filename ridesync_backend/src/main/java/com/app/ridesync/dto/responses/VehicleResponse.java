package com.app.ridesync.dto.responses;

import com.app.ridesync.entities.Vehicle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleResponse {
	
	private Vehicle vehicle;
	private String message;
    private boolean success;

}
