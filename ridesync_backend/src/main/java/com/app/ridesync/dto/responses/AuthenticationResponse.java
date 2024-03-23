package com.app.ridesync.dto.responses;

import java.util.List;

import com.app.ridesync.entities.Document;
import com.app.ridesync.entities.User;
import com.app.ridesync.entities.Vehicle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AuthenticationResponse {
    private String token;
    private User user;
    private List<Document> documents;
    private List<Vehicle> vehicles;
    private String message;
    private boolean success;

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
