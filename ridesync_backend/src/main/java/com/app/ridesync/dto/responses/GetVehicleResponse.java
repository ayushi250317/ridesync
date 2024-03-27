package com.app.ridesync.dto.responses;

import java.util.List;

import com.app.ridesync.entities.Vehicle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GetVehicleResponse {
	private List<Vehicle> vehicles;
	private String message;
    private boolean success;

}
