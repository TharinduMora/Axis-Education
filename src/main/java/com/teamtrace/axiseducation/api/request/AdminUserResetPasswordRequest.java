package com.teamtrace.axiseducation.api.request;

public class AdminUserResetPasswordRequest extends Request{
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
