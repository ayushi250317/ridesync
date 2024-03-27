package com.app.ridesync.dto.responses;

import java.util.List;

import com.app.ridesync.entities.RideRequestInfo;

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
public class RideRequestResponse {
    private List<RideRequestInfo> requests; 
    private String message;
    private boolean success;
}