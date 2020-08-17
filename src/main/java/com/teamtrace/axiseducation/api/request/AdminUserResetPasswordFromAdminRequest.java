package com.teamtrace.axiseducation.api.request;

public class AdminUserResetPasswordFromAdminRequest extends Request {
    private int adminUserId;

    public int getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(int adminUserId) {
        this.adminUserId = adminUserId;
    }
}
