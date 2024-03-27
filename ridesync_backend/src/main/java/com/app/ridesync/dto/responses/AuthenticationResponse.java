package com.app.ridesync.dto.responses;

import java.util.List;

import com.app.ridesync.entities.Document;
import com.app.ridesync.entities.User;
import com.app.ridesync.entities.Vehicle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthenticationResponse {
    private String token;
    private User user;
    private List<Document> documents;
    private List<Vehicle> vehicles;
    private String message;
    private boolean success;

}
