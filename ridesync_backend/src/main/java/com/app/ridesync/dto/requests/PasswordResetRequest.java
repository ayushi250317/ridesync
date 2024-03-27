package com.app.ridesync.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetRequest {
    private String token;
    private Integer id;
    private String newPassword;
    private String reNewPassword;
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    public void setReNewPassword(String reNewPassword) {
        this.reNewPassword = reNewPassword;
    }
    public void setId(Integer id) {
        this.id = id;
    }
}
