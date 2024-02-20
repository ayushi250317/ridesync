package com.app.ridesync.dto.responses;

import java.util.List;
import com.app.ridesync.entities.Vehicle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetVehicleResponse {
	private List<Vehicle> vehicles;
	private String message;
    private boolean success;

}
