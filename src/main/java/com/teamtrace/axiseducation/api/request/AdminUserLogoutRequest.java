package com.teamtrace.axiseducation.api.request;

public class AdminUserLogoutRequest extends Request {
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
