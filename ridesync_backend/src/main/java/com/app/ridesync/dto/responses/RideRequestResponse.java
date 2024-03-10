package com.app.ridesync.dto.responses;

import java.util.List;

import com.app.ridesync.entities.RideRequestInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RideRequestResponse {
    private List<RideRequestInfo> requests; 
    private String message;
    private boolean success;
}